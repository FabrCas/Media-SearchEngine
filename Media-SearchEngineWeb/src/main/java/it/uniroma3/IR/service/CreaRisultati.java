package it.uniroma3.IR.service; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import it.uniroma3.IR.model.RisultatoDoc;


public class CreaRisultati {
	//TODO
	private TopDocs risultatiRicerca;
	private String termineRicercato;
	private IndexSearcher searcher;
	private List<String> documentiRisultati;
	private List<RisultatoDoc> messaggioRicerca;
	
	public CreaRisultati(TopDocs fuzzyHits, String ricerca, IndexSearcher searcher) {
		this.risultatiRicerca=fuzzyHits;
		this.termineRicercato=ricerca;
		this.searcher= searcher;
		this.documentiRisultati= new ArrayList<String>();
		this.messaggioRicerca= new ArrayList<RisultatoDoc>();
		analizzaRisultati();
	} 
	
	public String getTotaleHits() {
		return "numero di documenti in cui ci sono stati riscontri per [" + this.termineRicercato
				+ "]: " + this.documentiRisultati.size()+ "\n";
	}
	
	private void analizzaRisultati() {
		System.out.println("Creazione risultati ricerca...");
		RisultatoDoc risultatoParziale;
		for (ScoreDoc score: risultatiRicerca.scoreDocs) {
			try {
				Document d= this.searcher.doc(score.doc);
				String nomeFile= d.get("Filetitle");
				if(!(this.documentiRisultati.contains(nomeFile))) {
					this.documentiRisultati.add(nomeFile);
					
				}
				else {
					
					
				}
				risultatoParziale= new RisultatoDoc();
				
				risultatoParziale.setFile(nomeFile);
				risultatoParziale.setTitolo(getNomeDocumento(nomeFile));
				risultatoParziale.setScore(modificaScore((score.toString())));
				messaggioRicerca.add(risultatoParziale);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<RisultatoDoc> getRisultatiDocumenti(){
		return this.messaggioRicerca;
	}
	
	private String getNomeDocumento(String titolo) {   //ovvero senza il .txt
		String nomeDocumento;
		nomeDocumento= titolo.replace(".jpg", "");
		System.out.println(nomeDocumento);
		return nomeDocumento;
	}
	
	private String modificaScore(String score) {
		String scoreMod="";
		Scanner scanner = new Scanner(score);
		if(scanner.hasNext())
			scoreMod=scoreMod + scanner.next() + " ";
		if(scanner.hasNext())
			scoreMod=scoreMod + scanner.next();
		scanner.close();
		return scoreMod;
	}
	
//	private String getPagina(String nomeFile) {
//		String nomePagina;
//		nomePagina= nomeFile.replace(".json", ".jpg");
//		return nomePagina;
//	}
	

}
