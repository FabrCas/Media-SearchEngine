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
import it.uniroma3.IR.model.RisultatoDoc;


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
	
	public String getTotaleHits() {
		return "numero di documenti in cui ci sono stati riscontri per [" + this.termineRicercato
				+ "]: " + this.mappingRisultatiPerNomeDoc.keySet().size()+ "\n"; //numero pagine (documenti) con riscontri
	} 
	
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
				//costruisco le coordinate di questa trascrizione
				Box boxParola= new Box();
			//	boxParola.setRiga(Integer.parseInt(d.get("riga")));
				boxParola.setX(Long.parseLong(d.get("xP")));
				boxParola.setY(Long.parseLong(d.get("yP")));
				boxParola.setWidth(Long.parseLong(d.get("wP")));
				boxParola.setHeight(Long.parseLong(d.get("hP")));
				boxParola.setTrascriptions(d.get("contents"));
				System.out.println((d.get("contents")));
				risultatoParziale.addBoxP(boxParola);
				this.mappingRisultatiPerNomeDoc.put(nomeFile, risultatoParziale);
				//risultatoParziale.setScore(modificaScore((score.toString())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<RisultatoDoc> getRisultatiDocumenti(){
		List <RisultatoDoc> lista=  new ArrayList<RisultatoDoc> (this.mappingRisultatiPerNomeDoc.values());
		return lista;
	}
	
	private String getNomeDocumento(String titolo) {   //ovvero senza il .txt
		String nomeDocumento;
		nomeDocumento= titolo.replace(".jpg", "");
		return nomeDocumento;
	}
	
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
	
	//utilizzato per risolvere il problema del caricamento dei file nella pagina dei risultati
	public  Set<String> getDocumentiConRiscontri() {
		return  this.mappingRisultatiPerNomeDoc.keySet();
	}
	
//	private String getPagina(String nomeFile) {
//		String nomePagina;
//		nomePagina= nomeFile.replace(".json", ".jpg");
//		return nomePagina;
//	}
	

}
