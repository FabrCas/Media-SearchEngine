package it.uniroma3.PrecisionRecall;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private int sizeRisultati;
	
	
	public CalcolatorePrecisionRecall() throws IOException {
		this.queries= new ArrayList<String>();
		this.risultatiPR= new ArrayList<PrecisionRecall>();
		this.CaricaParole();
		this.interrogatore= new Interrogatore();
		sizeRisultati= 0;
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
	
	public void generaRisultati() throws Exception {
		
		/*test con la prima query*/
		this.interrogatore.ricercaFuzzy(this.queries.get(0));
		CreaRisultati risultati = this.interrogatore.getRisultati();
		List<RisultatoDoc> listaRisultati= risultati.getRisultatiDocumenti();
		for (RisultatoDoc rd:listaRisultati ) {
			for(BoxTrascrizione boxP:rd.getBoxP() ) {
				String trascrizioni= boxP.getTrascriptions();
				
				
			}
		}
	}
	
//	public void calculate(SearchResults results, int hitLimit) {
//
//		double tp = results.relevantResults; // true positives
//		double fp = results.list.size() - tp; // false positives
//		double fn = relevantDocumentCount - results.relevantResults; // false
//																		// negatives
//
//		double precision = tp / (tp + fp);
//		double recall = tp / (tp + fn);
//
//		PrecisionRecall precisionRecall = new PrecisionRecall(precision, recall);
//		
//		this.allResults.add(precisionRecall);
//
//		// Save value if new relevant document was received
//		if(results.relevantResults > relevantDocumentsFound) {
//			// Found a new relevant document!
//			this.avgPrecisionResults.add(precisionRecall);
//			relevantDocumentsFound++;
//		}
//	}
	
	
	
	
	public void Stampa() {
		System.out.println("numero elementi "+ this.queries.size());
		System.out.println("elementi: ");
		for (String a: this.queries) {
			System.out.println(a);
		}
	}

}
