package it.uniroma3.IR.service;

import java.util.List;
import java.util.Scanner;

/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import it.uniroma3.IR.model.Coordinate;
import java.util.Map;*/

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ElaboraCampi {
	//private Map<String,List<Coordinate>> parolaPosizione;
	
	public ElaboraCampi() {
	}

//    public String getContenutoDocumento(JSONArray array ) {
//    String risultato="";
//    	for(int i=0; i< array.size(); i++) {
//    		JSONObject word= (JSONObject) array.get(i);
//    		JSONArray trascrizioni= (JSONArray) word.get("trascriptions");
//    		//System.out.println(trascrizioni.toString());
//    		for(Object x: trascrizioni) {
//    			String nome= (String) x;
//    			risultato+=(nome);
//    			risultato+=(" ");
//    		}
//    	}
//    	return risultato;	
//    }




	public String getContenutoTrascrizione(String trascrizioni) {
		String contenuto="";
		Scanner scanner= new Scanner(trascrizioni);
		scanner.useDelimiter("\n");
		while(scanner.hasNext()) {
			contenuto += scanner.next();
			if(scanner.hasNext())
				contenuto += " ";
		}
		scanner.close();
		return contenuto;
	}

	
	public String elaboraInfoDocumento(String documentoInfo, List<String> coordinateDocumento) {
		Scanner scanner= new Scanner(documentoInfo);
		scanner.useDelimiter("_");
		String titolo="";
		int i=0;
		while(scanner.hasNext()) {
			String value= scanner.next();
			if(i==0) {
				titolo= value;
			}
			else {
				coordinateDocumento.add(value);
			}
			i++;
		}
		// TODO Auto-generated method stub
		scanner.close();
		return titolo;
	}
	
	public void getCoordinateParola(String parolaInfo, List<String> coordinateParola) {
		Scanner scanner= new Scanner(parolaInfo);
		scanner.useDelimiter("_");
		while(scanner.hasNext()) {
			String value= scanner.next();
			coordinateParola.add(value);
		}
		scanner.close();
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
