package it.uniroma3.PrecisionRecall;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	private GroundTruth gd;
	private Map<String, Integer> occorrenzeGT;
	
	public CalcolatorePrecisionRecall() throws Exception {
		this.gd= new GroundTruth();
		occorrenzeGT= this.gd.getOccorrenze();
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
		
		double documentiRilevanti;
		if((this.occorrenzeGT.get(q))==null)
			documentiRilevanti=0;
		else
			documentiRilevanti= this.occorrenzeGT.get(q).doubleValue();	
		
		List<RisultatoDoc> listaRisultati= risultati.getRisultatiDocumenti();
		double tp= VerificaTruePositive(listaRisultati, q,documentiRilevanti );
		int size= this.tempSize;
		this.calcola(tp,size, q, documentiRilevanti);
		System.out.println("size: "+ size + " tp: "+ tp);
		i++;
	}
		double avgP = this.getPrecisionAVG();
		double avgR = this.getRecallAVG();
		System.out.println("precisione media: "+ avgP);
		System.out.println("Recall media: "+ avgR);
	}
	
	private void calcola(double tp, int size, String query, double documentiRilevanti) {
		
		System.out.println("docRil: "+ documentiRilevanti);
		double fp = size - tp; 
		double fn = documentiRilevanti - tp; 
		
		double precision;
		/*per evitare divisione per 0*/
		if (tp==0) {
			precision=0;
		}
		else {
		precision = tp / (tp + fp);
		}
		
		double recall = tp / (tp + fn);
		
		PrecisionRecall precisionRecall = new PrecisionRecall(precision, recall, query);	
		this.risultatiPR.add(precisionRecall);
	}
	
	private double VerificaTruePositive(List<RisultatoDoc> listaRisultati, String QueryAttuale, double documentiRilevanti) {
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
					if(trascrizioneN.equals(QueryAttuale)) {
						tp++;
						trovato=true;
					}
				}
				scanner.close();
			}
		}
		if (tp> documentiRilevanti)
			tp= documentiRilevanti;
		return tp;
	}
	
	
	public double getPrecisionAVG() {
		double nValoriPrecision = this.risultatiPR.size();
		double accumulatore= 0;
		for(PrecisionRecall pr:  this.risultatiPR) {
			System.out.println("************************************");
			System.out.println("Q: "+ pr.getQuery());
			System.out.println("P-------> "+pr.getPrecision());
			System.out.println("R-------> "+pr.getRecall());
			accumulatore += pr.getPrecision();
		}
		return accumulatore/nValoriPrecision;
	}
	
	public double getRecallAVG() {
		double nValoriRecall = this.risultatiPR.size();
		double accumulatore= 0;
		for(PrecisionRecall pr:  this.risultatiPR) {
			accumulatore += pr.getRecall();
		}
		return accumulatore/nValoriRecall;
	}
	
	
	public void Stampa() {
		System.out.println("numero elementi "+ this.queries.size());
	}

}
