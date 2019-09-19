package it.uniroma3.IR.service; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	
	
	public CreaRisultati(TopDocs fuzzyHits, String ricerca, IndexSearcher searcher) {
		this.risultatiRicerca=fuzzyHits;
		this.termineRicercato=ricerca;
		this.searcher= searcher;
	}
	
	public String totaleHits() {
		return "numero di documenti in cui ci sono stati riscontri per [" + this.termineRicercato
				+ "]: " + this.risultatiRicerca.totalHits.value+ "\n";
	}
	
	
	public List<RisultatoDoc> risultatiDocumenti(){
		System.out.println("Creazione risultati ricerca...");
		List<RisultatoDoc> messaggioRicerca= new ArrayList<RisultatoDoc>();
		RisultatoDoc risultatoParziale;
		for (ScoreDoc score: risultatiRicerca.scoreDocs) {
			try {
				risultatoParziale= new RisultatoDoc();
				Document d= this.searcher.doc(score.doc);
				String nomeFile= d.get("title");
				risultatoParziale.setFile(nomeFile);
				risultatoParziale.setTitolo(getNomeDocumento(nomeFile));
				risultatoParziale.setScore(modficaScore((score.toString())));
				messaggioRicerca.add(risultatoParziale);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return messaggioRicerca;
	}
	
	private String getNomeDocumento(String titolo) {   //ovvero senza il .txt
		String nomeDocumento;
		nomeDocumento= titolo.replace(".jpg", "");
		System.out.println(nomeDocumento);
		return nomeDocumento;
	}
	
//	private String getPagina(String nomeFile) {
//		String nomePagina;
//		nomePagina= nomeFile.replace(".json", ".jpg");
//		return nomePagina;
//	}
	
	private String modficaScore(String score) {
		String scoreMod="";
		Scanner scanner = new Scanner(score);
		if(scanner.hasNext())
			scoreMod=scoreMod + scanner.next() + " ";
		if(scanner.hasNext())
			scoreMod=scoreMod + scanner.next();
		scanner.close();
		return scoreMod;
	}
}
