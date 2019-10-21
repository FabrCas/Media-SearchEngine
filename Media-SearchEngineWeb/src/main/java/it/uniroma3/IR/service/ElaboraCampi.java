package it.uniroma3.IR.service;
/**
 * classe di supporto utilizzata dalla classe indicizzatrice,
 * svolge operazioni di separazione delle informazioni da formati presi dal .json
 * 
 * @see Indicizzatore
 **/

import java.util.List;
import java.util.Scanner;

public class ElaboraCampi {
	
	/**
	 * esempio formato trascrizioni: primates\nprimitis\npinnatis\n
	 * funzione sostituisce gli "\n" in " ", in modo da fornire poi a lucene
	 * il "contents", ovvero i termini che andranno indicizzati
	 **/
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

	/**
	 * esempio formato info documento :040r_195_275_1355_1807
	 * analizza la stringa sopra riportata, sepando le informazioni,
	 * utilizzando "_" come delimeter, il primo valore rappresenta il nome del documento,
	 * i 4 successivi le info sulle coordinate del documento, rispettivamente in ordine:
	 * x,y, larghezza, altezza
	 * 
	 * @param documentoInfo
	 * @param coordinateDocumento
	 * 
	 **/
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
	
	/**
	 * esempio formato info parola: 244_48_133_86
	 * analizza la stringa sopra riportata, sepando le informazioni,
	 * utilizzando "_" come delimeter, i valori contenuti riguardano le info sulle coordinate 
	 * del box della parola, rispettivamente: x,y, larghezza, altezza
	 * 
	 * @param parolaInfo
	 * @param coordinateParola
	 **/
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
