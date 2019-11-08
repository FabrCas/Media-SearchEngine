package it.uniroma3.IR.service; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import it.uniroma3.IR.model.Box;
import it.uniroma3.IR.model.BoxTrascrizione;
import it.uniroma3.IR.model.RisultatoDoc;


/**
 * classe che analizza e elabora i risultati creati da Interrogatore, per renderli 
 * utili nella visualizzazione, attraverso l'utilizzzo della classi di modello:
 * Box, BoxTrascrizione,RisultatoDoc.
 * 
 * @see RisultatoDoc
 * @see BoxTrascrizione
 * @see Box
 * @see Interrogatore
 * 
 */

public class CreaRisultati {
	private TopDocs risultatiRicerca;
	private String termineRicercato;
	private IndexSearcher searcher;
	private Map<String,RisultatoDoc> mappingRisultatiPerNomeDoc;

	public CreaRisultati(TopDocs fuzzyHits, String ricerca, IndexSearcher searcher) {
		this.risultatiRicerca=fuzzyHits;
		this.termineRicercato=ricerca;
		this.searcher= searcher;
		this.mappingRisultatiPerNomeDoc= new HashMap<String,RisultatoDoc>();
		analizzaRisultati();
	} 

	/**
	 * metodo che restituisce un messaggio contente le informazioni sui documenti in cui ci sono stati
	 * dei riscontri
	 */
	public String getTotaleHits() {
		return "number of documents for the research of [" + this.termineRicercato
				+ "]: " + this.mappingRisultatiPerNomeDoc.keySet().size()+ "\n";
	} 

	/**
	 * metodo che analizza i risultati di ricerca e li elabora per renderli utili alla visualizzazione
	 */
	private void analizzaRisultati() {

		System.out.println("Creazione risultati ricerca...");
		RisultatoDoc risultatoParziale;
		for (ScoreDoc score: risultatiRicerca.scoreDocs) {
			try {
				Document d= this.searcher.doc(score.doc);
				String nomeFile= d.get("Filetitle");

				//documento non presente nella lista dei risultati
				if(!(this.mappingRisultatiPerNomeDoc.containsKey(nomeFile))) {
					risultatoParziale= new RisultatoDoc(); 
					risultatoParziale.setFile(nomeFile);
					risultatoParziale.setTitolo(getNomeDocumento(nomeFile));
					risultatoParziale.setScore(Float.parseFloat(modificaScore(score.toString()))); 

					//costruisco il box del documento
					Box coordinateDocumento= new Box();
					coordinateDocumento.setX(Long.parseLong(d.get("xD")));
					coordinateDocumento.setY(Long.parseLong(d.get("yD")));
					coordinateDocumento.setWidth(Long.parseLong(d.get("wD")));
					coordinateDocumento.setHeight(Long.parseLong(d.get("hD")));
					risultatoParziale.setCoordinateD(coordinateDocumento);
				}
				//documento presente
				else {
					risultatoParziale= mappingRisultatiPerNomeDoc.get(nomeFile);
					/*c'è stato già un hit con uno score, il nuovo score si va a sommare*/
					risultatoParziale.addScore(Float.parseFloat(modificaScore((score.toString())))); 
				}
				//costruisco il box di questa trascrizione
				BoxTrascrizione boxParola= new BoxTrascrizione();

				/*riga da aggiungere quando si avrà l'aggiunta delle info su riga:
				boxParola.setRiga(Integer.parseInt(d.get("riga")));  */

				boxParola.setX(Long.parseLong(d.get("xP")));
				boxParola.setY(Long.parseLong(d.get("yP")));
				boxParola.setWidth(Long.parseLong(d.get("wP")));
				boxParola.setHeight(Long.parseLong(d.get("hP")));
				boxParola.setTrascriptions(d.get("contents"));
				risultatoParziale.addBoxP(boxParola);

				this.mappingRisultatiPerNomeDoc.put(nomeFile, risultatoParziale);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * metodo che aggiunge score a un documento 
	 * 
	 * @param score
	 * @return il nuovo score
	 */
	private String modificaScore(String score) {
		String scoreMod = "";
		Scanner scanner = new Scanner(score);
		if(scanner.hasNext())
			scanner.next();
		if(scanner.hasNext()) {
			scoreMod= scanner.next();
			scoreMod= scoreMod.substring(6);//scoreMod valore del tipo: score=0.32343434...
		}
		scanner.close();
		return scoreMod;
	}


	/**
	 * metodo che ritorna i documenti in cui ci sono stati riscontri
	 * 
	 * @return i documenti in cui ci sono stati riscontri
	 */
	//utilizzato per risolvere il problema del caricamento dei file nella pagina dei risultati
	public  Set<String> getDocumentiConRiscontri() {
		return  this.mappingRisultatiPerNomeDoc.keySet();
	}

	/**
	 * metodo che restituisce il nome del documento
	 * 
	 * @param titolo
	 * @return il nome del documento
	 */
	private String getNomeDocumento(String titolo) {   //ovvero senza il .txt
		String nomeDocumento;
		nomeDocumento= titolo.replace(".jpg", "");
		return nomeDocumento;
	}

	public List<RisultatoDoc> getRisultatiDocumenti(){
		List <RisultatoDoc> lista=  new ArrayList<RisultatoDoc> (this.mappingRisultatiPerNomeDoc.values());
		return lista;
	}

}
