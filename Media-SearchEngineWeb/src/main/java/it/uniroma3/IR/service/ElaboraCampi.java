package it.uniroma3.IR.service;

import java.util.List;
import java.util.Scanner;

public class ElaboraCampi {
	
	public ElaboraCampi() {}

	
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
}
