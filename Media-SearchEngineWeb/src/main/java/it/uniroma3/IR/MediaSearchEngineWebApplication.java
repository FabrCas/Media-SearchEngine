package it.uniroma3.IR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("it.uniroma3.IR.controller") //to scan packages mentioned
@ComponentScan("it.uniroma3.IR.service") //to scan packages mentioned
@ComponentScan("it.uniroma3.PrecisionRecall") //to scan packages mentioned
public class MediaSearchEngineWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaSearchEngineWebApplication.class, args);
	}

}
