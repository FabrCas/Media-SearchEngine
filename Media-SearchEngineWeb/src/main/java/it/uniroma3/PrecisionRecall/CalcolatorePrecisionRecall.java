package it.uniroma3.PrecisionRecall;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CalcolatorePrecisionRecall {
	
	
	private static final String WORDS_PATH= "src/main/resources/static/Precision_Recall/100words.txt";
	private List<String> queries;
	
	
	public CalcolatorePrecisionRecall() throws IOException {
		queries= new ArrayList<String>();
		this.CaricaParole();
		this.Stampa();
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
	
	private void Stampa() {
		System.out.println("numero elementi "+ this.queries.size());
		System.out.println("elementi: ");
		for (String a: this.queries) {
			System.out.println(a);
		}
		
	}
	
	

}
