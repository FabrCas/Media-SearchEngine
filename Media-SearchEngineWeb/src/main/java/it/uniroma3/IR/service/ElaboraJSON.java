package it.uniroma3.IR.service;

/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import it.uniroma3.IR.model.Coordinate;
import java.util.Map;*/

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ElaboraJSON {
	//private Map<String,List<Coordinate>> parolaPosizione;
	
	public ElaboraJSON() {
	}

    public String getContenuto(JSONArray array ) {
    String risultato="";
    	for(int i=0; i< array.size(); i++) {
    		JSONObject word= (JSONObject) array.get(i);
    		JSONArray trascrizioni= (JSONArray) word.get("trascriptions");
    		//System.out.println(trascrizioni.toString());
    		for(Object x: trascrizioni) {
    			String nome= (String) x;
    			risultato.concat(nome);
    			risultato.concat(" ");
    		}
    	}
    	return risultato;	
    }

    /*da utilizzare
    private Map<String,List<Coordinate>> aggiungiValoreMappa (JSONArray array) {
		this.parolaPosizione= new HashMap<String,List<Coordinate>>();
    	for(int i=0; i< array.size(); i++) {
    		JSONObject word= (JSONObject) array.get(i);
    		JSONArray trascrizioni= (JSONArray) word.get("trascriptions");
    		JSONObject posizione= (JSONObject) word.get("area");
    		//System.out.println(trascrizioni.toString());
    		for(Object trascrizione: trascrizioni) {
    			String nome= (String) trascrizione;
    			Long x= (Long) posizione.get("x");
    	    	Long y= (Long) posizione.get("y");
    	    	Long width= (Long) posizione.get("w");
    	    	Long height= (Long) posizione.get("h");
    	    	Coordinate coordinate= new Coordinate(x, y, width, height);
    	    	List<Coordinate> coordinatePresenti;
    	    	if (this.parolaPosizione.containsKey(nome)) {
    	    		coordinatePresenti= this.parolaPosizione.get(nome);
    	    	}
    	    	else {
    	    		coordinatePresenti= new ArrayList<Coordinate>();
    	    	}
    	    	coordinatePresenti.add(coordinate);
    	    	this.parolaPosizione.put(nome, coordinatePresenti);	
    		}
    	}
    	 return parolaPosizione;
    }   */
}
