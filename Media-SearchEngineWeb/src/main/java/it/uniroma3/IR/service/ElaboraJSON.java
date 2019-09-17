package it.uniroma3.IR.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.uniroma3.IR.model.Coordinate;

public class ElaboraJSON {
	private Map<String,List<Coordinate>> parolaPosizione;
	
	public ElaboraJSON() {
		this.parolaPosizione= new HashMap<String,List<Coordinate>>();
		
	}

    public String getContenuto(JSONArray array ) {
    String risultato="";
    	for(int i=0; i< array.size(); i++) {
    		JSONObject word= (JSONObject) array.get(i);
    		JSONArray trascrizioni= (JSONArray) word.get("trascriptions");
    		JSONObject posizione= (JSONObject) word.get("area");
    		//System.out.println(trascrizioni.toString());
    		for(Object x: trascrizioni) {
    			String nome= (String) x;
    			risultato.concat(nome);
    			risultato.concat(" ");
    			this.aggiungiValoreMappa(nome, posizione);
    		}
    	}
    	return risultato;	
    }

    private  void aggiungiValoreMappa (String nome, JSONObject pos) {
    	Long x= (Long) pos.get("x");
    	Long y= (Long) pos.get("y");
    	Long width= (Long) pos.get("w");
    	Long height= (Long) pos.get("h");
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

    public Map<String, List<Coordinate>> getMappaParolaPosizione() {
    	return this.parolaPosizione;
    }
}
