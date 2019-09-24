package it.uniroma3.IR.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
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
	private boolean isValida= true;
	
	
	
	public Interrogatore (){
		// creo il ricercatore di Lucene, che ricerca sopra a ogni indexReader
		 try {
			this.searcher = creaSearcher();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ricercaFuzzy(String testo) throws Exception {
		if(testo.equals("")) {
			System.out.println("ricerca non valida, nessun testo di ricerca aggiunto!\n");
			this.isValida= false;
			return;
		}
		String testoRicerca= this.analizzaQuery(testo);
		if(testoRicerca.trim().equals("")) {
			System.out.println("ricerca non valida, stai cercando uno o più: ' '!\n");
			this.isValida= false;
			return;
		}
		this.isValida= true;
		System.out.println("Ricerca nel motore di riceerca per:"+testoRicerca);
		/*primo modo:*/
		//Term termineRicerca= new Term("contents",testoRicerca+"~2");
		//Query fuzzyQuery= new Query(termineRicerca);  //2° parametro int maxEdits, ovvero la massima edit distance (n. operazioni per trasformare termine ricerca in termine ricercato
		/*massima edit distance = 2 (default)*/
		
		/*secondo modo:*/
		QueryParser qp= new QueryParser("contents", new StandardAnalyzer());
		Query fuzzyQuery= qp.parse(testoRicerca+"~2");
		
		//di default lucene fa l'OR tra le i vari termini della query
		//wildcard auto implementate, sintassi:
		//rimpiazzamento di un singolo carattere ->? 
		//rimpiazzamento di più caratteri -> *
		
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
	
	/*
	 * funzione utilizzate per prevenire gli errori nell'utilizzo dei caratteri chiari per 
	 * la ricerca con wildcard(? e *), quali:
	 * - termine chiave come primo carattere di una parola -> ?[parola] || *[parola]
	 * - termine chiave isolato da una parola prima e dopo -> ? [parola] || [parola] ? || * [parola] || [parola] *
	 * 
	 */
	private String analizzaQuery (String stringaRicerca) {
		String stringaRicercaAnalizzata="";
		String s1;
		String s2;
		Scanner scanner= new Scanner(stringaRicerca);
		while(scanner.hasNext()) {
			s1= scanner.next();
			
			//* o ? sono stringhe staccate? 
			if(!(s1.equals("*") || s1.equals("?"))) {				
				System.out.println(stringaRicercaAnalizzata);
				//verifico se s1 non contiene all'inizio * o ?
				if(!((s1.charAt(0)=='?') || (s1.charAt(0)=='*')))
					stringaRicercaAnalizzata += s1.trim();
				else {
					s2=(s1.subSequence(1, s1.length())).toString();
					System.out.println(s2);
					stringaRicercaAnalizzata += s2;
				}
			}
			if (scanner.hasNext())
				stringaRicercaAnalizzata += " ";
		}
		scanner.close();
		return stringaRicercaAnalizzata;
	}

	public CreaRisultati getRisultati () {
		return this.risultati;
	}
	
	public boolean isValida() {
		return this.isValida;
	}


}
