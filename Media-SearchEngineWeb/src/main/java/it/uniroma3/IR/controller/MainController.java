package it.uniroma3.IR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.IR.model.RisultatoDoc;
import it.uniroma3.IR.service.CreaRisultati;
import it.uniroma3.IR.service.Indicizzatore;
import it.uniroma3.IR.service.Interrogatore;


@Controller
public class MainController {
	
	@Autowired
	private Indicizzatore indicizzatore;
	
	@Autowired
	private Interrogatore interrogatore;

	//indicizzazione
	@RequestMapping(value="/Indexing", method= RequestMethod.POST)
	public String toIndex() {
		System.out.println("indicizzazione...\n");
		this.indicizzatore.indicizzaCartella();
		return "searchPage.html"; //per prova, da implementare con js
	}
	
	//interrogazione
	@RequestMapping(value="/toFind", method= RequestMethod.POST)
	public String toFind(@RequestParam("search_input") String ricerca, Model model) throws Exception{
		this.interrogatore.ricercaFuzzy(ricerca);
		CreaRisultati risultati= this.interrogatore.getRisultati();
		List<RisultatoDoc> listaRisultati=risultati.risultatiDocumenti();
		for(RisultatoDoc ris: listaRisultati) {
			System.out.println(ris.getTitolo());
		}
		model.addAttribute("listaRisultati", listaRisultati);
		model.addAttribute("hits",risultati.totaleHits());
		return "risultati.html";
	}
	
	
	
	@RequestMapping(value = { "/", "/index"})
	public String index(Model model) {
		
		return "searchPage.html";
	}
	
	
	@RequestMapping("/home")
	public String toHome(){
		return "searchPage.html";
	}

}
