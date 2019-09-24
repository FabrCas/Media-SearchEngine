package it.uniroma3.IR.service;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

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

import it.uniroma3.IR.model.Coordinate;

import org.json.simple.JSONArray;
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
    private ElaboraJSON elaboratore;
	
    public Indicizzatore() {
    	try {
    		this.id=0L;
    		this.elaboratore= new ElaboraJSON();
    		//istanza di org.apache.lucene.store.Directory
			this.dirIndexedFiles= FSDirectory.open(Paths.get(INDEX_DIR));
			//analizzatore con le stop word di default 
			this.analyzer= new StandardAnalyzer();
			//configurazione dell'indexWriter
			this.iwc= new IndexWriterConfig(analyzer);
			this.iwc.setOpenMode(OpenMode.CREATE);                              //Creates a new index if one does not exist, otherwise it opens the index and documents will be appended.
			//l'indexWriter, scrive nuovi index file per la cartella "static/indexedFiles"
			this.writer= new IndexWriter(dirIndexedFiles, iwc);
			this.counterDocsIndexed=0;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //metodo che elabora i file .json nella direcory "inputFiles" e ritorna la classe per gestire la risposta
    public void indicizzaCartella() {
    	File file= new File(INPUT_DIR); //folder
    	File[] fileArray = file.listFiles(); //array of json files
    	System.out.println("\nContenuto documenti:\n");
    	//oggetto json parser per analizzare il file
    	JSONParser jsonParser= new JSONParser();
    	FileReader reader;
    	for(File f: fileArray) {
    		try {
    			this.elaboratore= new ElaboraJSON();
				reader= new FileReader(f);
				JSONObject jonb= (JSONObject)jsonParser.parse(reader);
				String nomeFile= (String) jonb.get("file-name");
				//System.out.println("Sto caricando il file: "+ nomeFile + " ...");
				JSONArray jArray= (JSONArray) jonb.get("words");
				this.counterDocsIndexed++;
				this.scorriDocumento(nomeFile, jArray);
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
    private void scorriDocumento(String nomeFile, JSONArray words) {
    	System.out.println("\n******************************************************************");
    	int numeroTrascrizioni= words.size();
    	for(int i=0;i<numeroTrascrizioni;i++) {
    		JSONObject elemento_i= (JSONObject)words.get(i);
    		String contenuto_i= this.elaboratore.getContenutoTrascrizione(elemento_i);
    		Coordinate coordinate_i= this.elaboratore.getCoordinateTrascrizione(elemento_i);

    		/*Stampe di prova*/
    		//System.out.println(jArray); //array composto da oggetti, con due proprietà: area e trascription
    		System.out.println("\nil contenuto della trascrizione " + this.id
    				+ ", del documento n°: " + this.counterDocsIndexed + ":");
    		System.out.println("trascrizioni: "+ contenuto_i);
    		System.out.println("coordinate:" + coordinate_i);
    		/*fine stampe di prova*/
    		indicizzaTrascrizione(nomeFile, contenuto_i,coordinate_i, this.id);
    		this.id++;
    	}
    	System.out.println("\n******************************************************************");
    }


    private void indicizzaTrascrizione(String titolo, String corpo,Coordinate coordinate, Long id) {
        try {
        	//creazione di un documento di Lucene
            Document document = new Document();
            document.add(new StringField("Filetitle", titolo, Field.Store.YES));
            document.add(new TextField("contents", corpo, Field.Store.YES));
            document.add(new StringField("id",id.toString() , Field.Store.YES));
            document.add(new StoredField("x", coordinate.getX()));
            document.add(new StoredField("y", coordinate.getY()));
            document.add(new StoredField("w", coordinate.getWidth()));
            document.add(new StoredField("h", coordinate.getHeight()));
            //document.add(x,y,width,height
            writer.addDocument(document);
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//    public void indicizzaDocumento(String titolo, String corpo, Long id) {
//        try {
//        	//creazione di un documento di Lucene
//            Document document = new Document();
//            document.add(new StringField("title", titolo, Field.Store.YES));
//            document.add(new TextField("contents", corpo, Field.Store.YES));
//            document.add(new StringField("id",id.toString() , Field.Store.YES));
//            
//            writer.addDocument(document);
//            writer.commit();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    public int getCounterDocsIndexed() {
		return counterDocsIndexed;
	}
    
    public Long getLastId() {
    	return this.id; 
    }
}
