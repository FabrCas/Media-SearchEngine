package it.uniroma3.PrecisionRecall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class GroundTruth {
	private static final String GT_PATH= "src/main/resources/static/Precision_Recall/Ground_truth/";
	private Map<String,Integer> occorrenzeGT;
	private static String[] files= {"040r", "040v", "041r", "041v", "042r" ,"042v", "043r", "043v",
			"044r", "044v", "050v", "100r", "150v", "200r"};
	
	public GroundTruth() throws FileNotFoundException {
		this.occorrenzeGT= new HashMap<String, Integer>();
		this.calcolaOccorrenze();
		//this.stampa();
	}
	
	private void calcolaOccorrenze() throws FileNotFoundException {
		for(String nomeF: files) {
			String completePath= GT_PATH + nomeF + ".txt";
			File file= new File(completePath);
			this.elaboraFile(file);
		}
	}
	
	private void inserisciParola(String parola) {
		if(this.occorrenzeGT.containsKey(parola)) {
			int intero =this.occorrenzeGT.get(parola).intValue() +1;
			occorrenzeGT.put(parola, intero);
		}
		else {
			occorrenzeGT.put(parola, 1);
		}
	}
	
	private void elaboraFile(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file );
		sc.useDelimiter(" ");
		while(sc.hasNext()) {
			String parola = sc.next().toLowerCase().trim();  //cancella spazi e upCase per unificare occorrenze
			if (parola.contains("\n") ) {
				String [] parole = parola.split("\n");
				String nuovaparola1 = parole[0];
				nuovaparola1= nuovaparola1.replace("\n", "").replace("\r", "");
				String nuovaparola2 = parole[1];
				this.inserisciParola(this.ripulisci(nuovaparola1));
				this.inserisciParola(this.ripulisci(nuovaparola2));
				}
			else {
				this.inserisciParola(this.ripulisci(parola));
			}
		}
		sc.close();
	}
	
	private String ripulisci(String parola) {
		String nuovaparola= parola;
		nuovaparola =nuovaparola.replaceAll( "\\(.*?\\)|\\'.*?\\'|\\[.*?\\]|\\{.*?\\}", "");
		nuovaparola= nuovaparola.replace("v", "u");
		nuovaparola= nuovaparola.replace("j", "i");
		nuovaparola= nuovaparola.replace("r(um)", "rum");
		nuovaparola=nuovaparola.replace("p(er)", "per");
		nuovaparola=nuovaparola.replace("p(ro)", "pro");
		nuovaparola=nuovaparola.replace("q(ui)", "qui");
		nuovaparola=nuovaparola.replace("q(ue)", "que");
		nuovaparola=nuovaparola.replace("b(us)", "bus");
		nuovaparola=nuovaparola.replace("(rum)", "rum");
		nuovaparola=nuovaparola.replace("(per)", "per");
		nuovaparola=nuovaparola.replace("(pro)", "pro");
		nuovaparola=nuovaparola.replace("(qui)", "qui");
		nuovaparola=nuovaparola .replace("(que)", "que");
		nuovaparola=nuovaparola.replace("(bus)", "bus");
		nuovaparola=nuovaparola.replace("(con)", "con");
		nuovaparola=nuovaparola.replace("(et)", "et");
		nuovaparola=nuovaparola.replace("-", "");
		nuovaparola=nuovaparola.replace(",", "");
		nuovaparola=nuovaparola.replace(".", "");
		return nuovaparola;
	}
	
	
	public Map<String,Integer> getOccorrenze() {
		return this.occorrenzeGT;
	}
	
	private void stampa() {
		for (String key: this.occorrenzeGT.keySet()) {
			System.out.println("key: "+ key + " valore: "+ this.occorrenzeGT.get(key));
		}
	}
}
