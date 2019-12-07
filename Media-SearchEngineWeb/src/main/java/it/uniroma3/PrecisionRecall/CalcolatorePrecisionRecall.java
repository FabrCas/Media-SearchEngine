package it.uniroma3.PrecisionRecall;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import it.uniroma3.IR.model.BoxTrascrizione;
import it.uniroma3.IR.model.RisultatoDoc;
import it.uniroma3.IR.service.CreaRisultati;
import it.uniroma3.IR.service.Interrogatore;

@Component
public class CalcolatorePrecisionRecall {
	
	private Interrogatore interrogatore;
	private static final String WORDS_PATH= "src/main/resources/static/Precision_Recall/100words.txt";
	private List<String> queries;
	private List<PrecisionRecall> risultatiPR;
	private int tempSize;
	
	
	public CalcolatorePrecisionRecall() throws Exception {
		this.queries= new ArrayList<String>();
		this.risultatiPR= new ArrayList<PrecisionRecall>();
		this.CaricaParole();
		this.interrogatore= new Interrogatore();
		this.generaRisultati();
	}
	
	
	private void CaricaParole() throws IOException {
		File file= new File(WORDS_PATH);
		FileReader fReader= new FileReader(file);               
		BufferedReader reader = new BufferedReader(fReader);
		String line= reader.readLine();
		while (line != null) {
			queries.add(line);
			line= reader.readLine();
		}
		reader.close();
	}
	
	private void generaRisultati() throws Exception {
	System.out.println("****************Calcolo Precision & Recall******************************");
	int i=1;
	for(String q: this.queries) {
		System.out.println("*****************Stato: "+ i +"/100 ****************************************" );
		this.interrogatore.ricercaFuzzy(q);
		CreaRisultati risultati = this.interrogatore.getRisultati();
		List<RisultatoDoc> listaRisultati= risultati.getRisultatiDocumenti();
		double tp= VerificaTruePositive(listaRisultati, q);
		int size= this.tempSize;
		this.calcola(tp,size);
		System.out.println("size: "+ size + " tp: "+ tp);
		i++;
	}
		double avgP = this.getPrecisionAVG();
		System.out.println("precisione media: "+ avgP);
	}
	
	private void calcola(double tp, int size) {

		double fp = size - tp; 
//		double fn = relevantDocumentCount - tp; // false
		double precision;
		if ((tp+fp)==0)
			precision=0;
		else
			precision = tp / (tp + fp);
		
//		double recall = tp / (tp + fn);
		double recall= 0;
		
		PrecisionRecall precisionRecall = new PrecisionRecall(precision, recall);	
		this.risultatiPR.add(precisionRecall);
	}
	
	private double VerificaTruePositive(List<RisultatoDoc> listaRisultati, String QueryAttuale) {
		System.out.println("vtp, queryAttuale: "+ QueryAttuale);
		this.tempSize=0;
		double tp= 0;
		for (RisultatoDoc rd:listaRisultati ) {
			for(BoxTrascrizione boxP:rd.getBoxP() ) {
				String trascrizioni= boxP.getTrascriptions();
				tempSize ++;
				Scanner scanner= new Scanner(trascrizioni);
				scanner.useDelimiter(" ");
				boolean trovato= false;
				while(scanner.hasNext() && !(trovato)) {
					String trascrizioneN= scanner.next();
					//System.out.println("trascrizione "+ tempSize +" : "+ trascrizioneN);
					if(trascrizioneN.equals(QueryAttuale)) {
						tp++;
						trovato=true;
					}
				}
				scanner.close();
			}
		}
		return tp;
	}
	
	public double getPrecisionAVG() {
		double nValoriPrecision = this.risultatiPR.size();
		double accumulatore= 0;
		for(PrecisionRecall pr:  this.risultatiPR) {
			System.out.println("p-------> "+pr.getPrecision());
			accumulatore += pr.getPrecision();
		}
		return accumulatore/nValoriPrecision;
	}
	
	
	public void Stampa() {
		System.out.println("numero elementi "+ this.queries.size());
	}

}
