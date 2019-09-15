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
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.springframework.stereotype.Component;

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
	
    public Indicizzatore() {
    	try {
    		
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
    
    //metodo che elabora i file .json nella direcory "inputFiles"
    public void indicizzaCartella() {
    	File file= new File(INPUT_DIR); //folder
    	File[] fileArray = file.listFiles(); //array of json files
    	//oggetto json parser per analizzare il file

    	JSONParser jsonParser= new JSONParser();
    	FileReader reader;
    	/*BufferedReader bufReader;
    	String line;*/
    	for(File f: fileArray) {
    		try {
    			//System.out.println("--------------------------------------------\n");
				reader= new FileReader(f);
				/*bufReader = new BufferedReader(reader);
	    		
	    		line= bufReader.readLine();
	    		while (line!=null) {
	    			System.out.println(line);
	    			line= bufReader.readLine();}*/
	    			
	    		
				JSONObject jonj= (JSONObject)jsonParser.parse(reader);
				
				System.out.println("Sto caricando il file: "+ jonj.get("file-name") + " ...");
				
				
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
    
    
    public void indicizzaDocumento(String titolo, String corpo, File file ) {
        try {
        	//creazione di un documento di Lucene
            Document document = new Document();

            document.add(new StringField("title", titolo, Field.Store.YES));
            document.add(new TextField("contents", corpo, Field.Store.YES));
            document.add(new StringField("path", file.toString(), Field.Store.YES));

            writer.addDocument(document);
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
	
}
