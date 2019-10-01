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

 @Component
public class Indicizzatore {
	
	/* folders, paths*/
	//output folder
	private static final String INDEX_DIR= "src/main/resources/static/indexedFiles";
	//input folder
	private Directory dirIndexedFiles;
	private static final String INPUT_DIR="src/main/resources/static/inputFiles";
	/*lucene's objects*/
    private Analyzer analyzer;
	private IndexWriterConfig iwc;
    private IndexWriter writer ;
    private int counterDocsIndexed;
    
	/*L'analizzatore identifica in modo univoco i documenti che analizza*/
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
			this.iwc.setOpenMode(OpenMode.CREATE);                              //Creates a new index if one does not exist, otherwise it opens the index and documents will be appended.
			//l'indexWriter, scrive nuovi index file per la cartella "static/indexedFiles"
			this.writer= new IndexWriter(dirIndexedFiles, iwc);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //metodo che elabora i file .json nella direcory "inputFiles" e ritorna la classe per gestire la risposta
    //per ora un solo file 
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
//				String nomeFile= (String) jonb.get("file-name");
//				JSONArray jArray= (JSONArray) jonb.get("words");
				this.scorriFile(jonb);
				reader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }
    
    //funzione che invoca n volte, con n il numero di trascrizione in un documento, la funzione 
    //indicizza trascrizione
    private void scorriFile(JSONObject joFile) {
    	JSONObject pagineDocumento= (JSONObject) joFile.get("bbxs");
    	int numeroDocumenti= pagineDocumento.size();
    	System.out.println("numero documenti da indicizzare: "+ numeroDocumenti+ "\n");
    	
		@SuppressWarnings("unchecked")
		Set<String> chiaviDocumenti = pagineDocumento.keySet();
    	Iterator<String> iteratore= chiaviDocumenti.iterator();
    	while(iteratore.hasNext()) {    	
    		List <String> coordinateDocumento = new ArrayList<String>();

    		String documentoInfo= (String) iteratore.next();
 //   		System.out.println(documentoInfo);
    		String titoloDocumento= this.elaboratore.elaboraInfoDocumento(documentoInfo, coordinateDocumento );
    		//Document document = new Document();
//    		System.out.println(titoloDocumento);
//    		for(String a: coordinateDocumento) {
//    			System.out.println("*"+ a);
//    		}
    		this.counterDocsIndexed++;
    		JSONObject paroleDocumento =  (JSONObject) pagineDocumento.get(documentoInfo);
    		this.scorriPagina(paroleDocumento, titoloDocumento, coordinateDocumento);
    		
    	}
    }

    
    private void scorriPagina(JSONObject paroleDocumento, String titoloDocumento, List<String> coordinateDocumento) {
    	int numeroParoleDoc= paroleDocumento.size();
 
    	//System.out.println("\n*************start Parole*****************************************************");
    	System.out.println("Doc: "+ this.counterDocsIndexed+ "\n"+ "numero parole Doc: "+ numeroParoleDoc +"\n");
    	@SuppressWarnings("unchecked")
		Set<String> chiaviParole = paroleDocumento.keySet();
    	Iterator<String> iteratore= chiaviParole.iterator();
    	while(iteratore.hasNext()) {
    		List <String> coordinateparola = new ArrayList<String>();
    		String parolaInfo = (String) iteratore.next();
    		//System.out.println(parolaInfo);
    		this.elaboratore.getCoordinateParola(parolaInfo, coordinateparola);
    			//System.out.println(coordinateparola);
    		String trascrizioni = (String) paroleDocumento.get(parolaInfo);
    	    this.scorriTrascrizioni(trascrizioni ,titoloDocumento,
    	    		coordinateDocumento, coordinateparola, this.id);
    		this.id ++;
    	}
    	//System.out.println("\n***************end Parole***************************************************");
    }
    
    
    
    private void scorriTrascrizioni(String trascrizioni, String titoloDoc, List<String> coordDoc,
    		List<String> coordParola, Long id) {
    	String contenutoParola= "";
    	contenutoParola = this.elaboratore.getContenutoTrascrizione(trascrizioni);
    	//System.out.println(contenutoParola+ " id-> "+ id);
    	this.indicizzaTrascrizione(titoloDoc, coordDoc,  contenutoParola, coordParola, id );
    	
    }
    


    //scelgo l'indicizzazione per trascrizione rispetto a quella per documento
    private void indicizzaTrascrizione(String titoloDocumento, List<String> coordinateDocumento, String TrascrizioniParola,
    		List<String> coordinateParola,Long id) {
    	System.out.println("---->"+id);
        try {
        	//creazione di un documento di Lucene
        	Document document= new Document();
        	/*info documento*/
            document.add(new StringField("Filetitle", titoloDocumento, Field.Store.YES));
            document.add(new StoredField("xD", coordinateDocumento.get(0)));
            document.add(new StoredField("yD", coordinateDocumento.get(1)));
            document.add(new StoredField("wD", coordinateDocumento.get(2)));
            document.add(new StoredField("hD", coordinateDocumento.get(3)));
            /*info parola*/
            document.add(new TextField("contents", TrascrizioniParola, Field.Store.YES)); 
            document.add(new StoredField("xP", coordinateParola.get(0)));
            document.add(new StoredField("yP", coordinateParola.get(1)));
            document.add(new StoredField("wP", coordinateParola.get(2)));
            document.add(new StoredField("hP", coordinateParola.get(3)));
            document.add(new StringField("id",id.toString() , Field.Store.YES));
            //document.add(x,y,width,height
            writer.addDocument(document);
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getCounterDocsIndexed() {
		return counterDocsIndexed;
	}
    
    public Long getLastId() {
    	return this.id; 
    }
}
