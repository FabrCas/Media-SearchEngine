package it.uniroma3.IR.service; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import it.uniroma3.IR.model.Coordinate;
import it.uniroma3.IR.model.RisultatoDoc;


public class CreaRisultati {
	//TODO
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
				+ "]: " + this.mappingRisultatiPerNomeDoc.keySet().size()+ "\n";
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
				}
				//documento presente
				else {
					risultatoParziale= mappingRisultatiPerNomeDoc.get(nomeFile);
				}
				//costruisco le coordinate di questa trascrizione
				Coordinate coordinata= new Coordinate();
				coordinata.setX(Long.parseLong(d.get("x")));
				coordinata.setY(Long.parseLong(d.get("y")));
				coordinata.setWidth(Long.parseLong(d.get("w")));
				coordinata.setHeight(Long.parseLong(d.get("h")));
				risultatoParziale.addCoordinate(coordinata);
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
		System.out.println(nomeDocumento);
		return nomeDocumento;
	}
	
	private String modificaScore(String score) {
		String scoreMod="";
		Scanner scanner = new Scanner(score);
		if(scanner.hasNext())
			scanner.next();
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
