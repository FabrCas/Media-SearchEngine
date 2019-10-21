package it.uniroma3.IR.service;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * classe che permette l'indicizzazione di documenti, in questo caso effettuata sulle singole trascrizioni
 * di un documento (quindi ogni parola è vista come un documento), tramite il framework di Lucene.
 * 
 **/
@Component
public class Indicizzatore {

	/* folders, paths*/
	//output folder
	private static final String INDEX_DIR= "src/main/resources/static/indexedFiles";
	private Directory dirIndexedFiles;
	//input folder
	private static final String INPUT_DIR="src/main/resources/static/inputFiles";
	/*lucene's objects*/
	private Analyzer analyzer;
	private IndexWriterConfig iwc;
	private IndexWriter writer ;

	/*progressivo per il numero di pagine (documenti)*/
	private int counterDocsIndexed;

	/*L'analizzatore identifica in modo univoco le trascrizioni che analizza*/
	private Long id;

	/*utilità*/
	private ElaboraCampi elaboratore;

	public Indicizzatore() {

		try {
			this.elaboratore= new ElaboraCampi();
			//istanza di org.apache.lucene.store.Directory
			this.dirIndexedFiles= FSDirectory.open(Paths.get(INDEX_DIR));
			//analizzatore con le stop word di default 
			this.analyzer= new StandardAnalyzer();
			//configurazione dell'indexWriter
			this.iwc= new IndexWriterConfig(analyzer);
			this.iwc.setOpenMode(OpenMode.CREATE); //Creates a new index if one does not exist, otherwise it opens the index and documents will be appended.
			//l'indexWriter, scrive nuovi index file per la cartella "static/indexedFiles"
			this.writer= new IndexWriter(dirIndexedFiles, iwc);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * metodo che elabora i file .json nella direcory "inputFiles", per indicizzarli.
	 * Chiama un processo di indicizzazione su ogni singolo file presente nella cartella degl input,
	 * utilizzando un JSONPARSER per fornire le informazioni lette da un .json al metodo che analizza il
	 * singolo file preso in esame.
	 **/

	public void indicizzaCartella() {

		this.id=0L;
		this.counterDocsIndexed=0;
		File file= new File(INPUT_DIR); //folder
		File[] fileArray = file.listFiles(); //array of json files
		System.out.println("\nContenuto documenti:\n");
		//oggetto json parser per analizzare il file
		JSONParser jsonParser= new JSONParser();
		FileReader reader;
		for(File f: fileArray) {
			try {
				this.elaboratore= new ElaboraCampi();
				reader= new FileReader(f);
				JSONObject jonb= (JSONObject)jsonParser.parse(reader);
				this.scorriFile(jonb);
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
	}
	/**
	 * metodo che prende in input tutte le info di un file .json, sotto forma di un 
	 * JSONObject e elabora i dati di ogni documento, poi su ogni documento richiama una
	 * funzione per elaborare i dati su ogni singola parola.
	 * 
	 * @param joFile, oggetto che contiene tutte le info sul file (info su più documenti)
	 * @see ElaboraCampi
	 **/

	private void scorriFile(JSONObject joFile) {

		/*bbxs chiave con attributo tutte le info del file*/
		JSONObject pagineDocumento= (JSONObject) joFile.get("bbxs");
		int numeroDocumenti= pagineDocumento.size();
		System.out.println("numero documenti da indicizzare: "+ numeroDocumenti+ "\n");

		@SuppressWarnings("unchecked")
		Set<String> chiaviDocumenti = pagineDocumento.keySet();

		Iterator<String> iteratore= chiaviDocumenti.iterator();
		while(iteratore.hasNext()) {    	
			List <String> coordinateDocumento = new ArrayList<String>();

			String documentoInfo= (String) iteratore.next();
			String titoloDocumento= this.elaboratore.elaboraInfoDocumento(documentoInfo, coordinateDocumento );
			this.counterDocsIndexed++;
			/*prendo l'oggetto con le info sulle parole*/
			JSONObject paroleDocumento =  (JSONObject) pagineDocumento.get(documentoInfo);
			this.scorriPagina(paroleDocumento, titoloDocumento, coordinateDocumento);

		}
	}

	/**
	 * 
	 * metodo che elabora i dati su tutte le parole di un singolo documento, richiamando il metodo
	 * scorriTrascrizioni, per analizzare poi ogni singola parola o trascrizione.
	 * 
	 * @param paroleDocumento
	 * @param titoloDocumento
	 * @param coordinateDocumento
	 */
	private void scorriPagina(JSONObject paroleDocumento, String titoloDocumento, List<String> coordinateDocumento) {

		int numeroParoleDoc= paroleDocumento.size();
		System.out.println("Doc: "+ this.counterDocsIndexed+ "\n"+ "numero parole Doc: "+ numeroParoleDoc +"\n");
		/*prendo le chiavi delle parole*/
		@SuppressWarnings("unchecked")
		Set<String> chiaviParole = paroleDocumento.keySet();
		/*le scorro e elaboro le info*/ 
		Iterator<String> iteratore= chiaviParole.iterator();
		while(iteratore.hasNext()) {
			List <String> coordinateparola = new ArrayList<String>();
			String parolaInfo = (String) iteratore.next();
			this.elaboratore.getCoordinateParola(parolaInfo, coordinateparola);
			String trascrizioni = (String) paroleDocumento.get(parolaInfo);
			this.scorriTrascrizioni(trascrizioni ,titoloDocumento,
					coordinateDocumento, coordinateparola, this.id);
			this.id ++;
		}
	}

	/**
	 * metodo che elabora le info su ogni singola parola o trascrizione, che contiene più
	 * possibili significati.
	 * processo finale per la scomposizione delle informazioni dal file sorgente, che chiama il metodo
	 * che effettua l'indicizzazione.
	 * 
	 * @param trascrizioni
	 * @param titoloDoc
	 * @param coordDoc
	 * @param coordParola
	 * @param id
	 */
	private void scorriTrascrizioni(String trascrizioni, String titoloDoc, List<String> coordDoc,
			List<String> coordParola, Long id) {

		String contenutoParola= "";
		contenutoParola = this.elaboratore.getContenutoTrascrizione(trascrizioni);
		this.indicizzaTrascrizione(titoloDoc, coordDoc,  contenutoParola, coordParola, id);

	}

	/**
	 * 
	 * metodo che svolge l'indicizzazione, crea un documento di Lucene, ne aggiunge i campi da memorizzare,
	 * sia quelli sulle info del documento che quelle sulle info della parola, aggiunge il documento pronto
	 * per l'indicizzazione e fa un commit
	 * 
	 * @param titoloDocumento
	 * @param coordinateDocumento
	 * @param TrascrizioniParola
	 * @param coordinateParola
	 * @param id
	 */
	private void indicizzaTrascrizione(String titoloDocumento, List<String> coordinateDocumento, String TrascrizioniParola,
			List<String> coordinateParola,Long id) {
		/*stampa per il controllo del progresso del processo di indicizzazione*/
		System.out.println("---->"+id);

		try {
			/*creazione di un documento di Lucene*/
			Document document= new Document();
			/* aggiungo le info del documento*/
			document.add(new StringField("Filetitle", titoloDocumento, Field.Store.YES));
			document.add(new StoredField("xD", coordinateDocumento.get(0)));
			document.add(new StoredField("yD", coordinateDocumento.get(1)));
			document.add(new StoredField("wD", coordinateDocumento.get(2)));
			document.add(new StoredField("hD", coordinateDocumento.get(3)));
			/*info parola*/
			document.add(new TextField("contents", TrascrizioniParola, Field.Store.YES)); 

			/*riga da aggiungere quando si avrà l'aggiunta delle info su riga:
            document.add(new StoredField("riga", String.valueOf(riga)));*/

			/*aggiungo le info della parola*/
			document.add(new StoredField("xP", coordinateParola.get(0)));
			document.add(new StoredField("yP", coordinateParola.get(1)));
			document.add(new StoredField("wP", coordinateParola.get(2)));
			document.add(new StoredField("hP", coordinateParola.get(3)));
			document.add(new StringField("id",id.toString() , Field.Store.YES));

			/*aggiungo il documento all'indice*/
			writer.addDocument(document);
			/*faccio un commit per il documento aggiunto*/
			writer.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/*metodi get utili*/

	public int getCounterDocsIndexed() {
		return counterDocsIndexed;
	}

	public Long getLastId() {
		return this.id; 
	}
}
