package it.uniroma3.IR.service;

import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

@Component
public class Interrogatore {
	
	
	private static final String INDEX_DIR= "src/main/resources/static/indexedFiles";
	private static final int NUM_RESULT= 1000;
	
	private IndexSearcher searcher;
	private CreaRisultati risultati; 
	//private Analyzer analyzer;
	private IndexReader reader;
	
	
	public Interrogatore (){
		// creo il ricercatore di Lucene, che ricerca sopra a ogni indexReader
		 try {
			this.searcher = creaSearcher();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ricercaFuzzy(String testoRicerca) throws Exception {
		Term termineRicerca= new Term("contents",testoRicerca);
		FuzzyQuery fuzzyQuery= new FuzzyQuery(termineRicerca);  //2° parametro int maxEdits, ovvero la massima edit distance (n. operazioni per trasformare termine ricerca in termine ricercato
		/*massima edit distance = 2 (default)*/
		TopDocs fuzzyHits= this.searcher.search(fuzzyQuery,NUM_RESULT);
		this.risultati= new CreaRisultati(fuzzyHits, testoRicerca, this.searcher);
	}
	
	private IndexSearcher creaSearcher() throws IOException{
		
		Directory dir= FSDirectory.open(Paths.get(INDEX_DIR));
		
		//comunico all'interfaccia reader (che accede in ogni momento all'indice di Lucene) la directory con i token
		this.reader= DirectoryReader.open(dir);
		
		//l'index searcher
		IndexSearcher searcher= new IndexSearcher (reader);
		return searcher;
	}
	
	
	public CreaRisultati getRisultati () {
		return this.risultati;
	}

}
