package it.uniroma3.IR.service;

/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import it.uniroma3.IR.model.Coordinate;
import java.util.Map;*/

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.uniroma3.IR.model.Coordinate;

public class ElaboraJSON {
	//private Map<String,List<Coordinate>> parolaPosizione;
	
	public ElaboraJSON() {
	}

    public String getContenutoDocumento(JSONArray array ) {
    String risultato="";
    	for(int i=0; i< array.size(); i++) {
    		JSONObject word= (JSONObject) array.get(i);
    		JSONArray trascrizioni= (JSONArray) word.get("trascriptions");
    		//System.out.println(trascrizioni.toString());
    		for(Object x: trascrizioni) {
    			String nome= (String) x;
    			risultato+=(nome);
    			risultato+=(" ");
    		}
    	}
    	return risultato;	
    }


	public Coordinate getCoordinateTrascrizione(JSONObject elemento_i) {
		Coordinate coordinate_i= new Coordinate();
		JSONObject area_i= (JSONObject)elemento_i.get("area");
		coordinate_i.setX((Long) area_i.get("x"));
		coordinate_i.setY((Long) area_i.get("y"));
		coordinate_i.setHeight((Long) area_i.get("h"));
		coordinate_i.setWidth((Long) area_i.get("w"));
		return coordinate_i;
	}

	public String getContenutoTrascrizione(JSONObject elemento_i) {
		String contenuto="";
		JSONArray trascrizioni_i= (JSONArray) elemento_i.get("trascriptions");
		int lunghezza = trascrizioni_i.size();
		int iterCount=0;
		for(Object trascrizione_JSON: trascrizioni_i) {
			String trascrizione= (String) trascrizione_JSON;
			contenuto += trascrizione;
			iterCount++;
			if(!(iterCount==lunghezza))
				contenuto+= " ";
		}
		return contenuto;
	}


    /*da utilizzare se si vuole avere un mapping in memoria centrale
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
