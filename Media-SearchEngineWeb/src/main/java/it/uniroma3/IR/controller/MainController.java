package it.uniroma3.IR.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.uniroma3.IR.comparatore.RisultatatoDocComparatore;
import it.uniroma3.IR.model.Coordinate;
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
	@RequestMapping(value="/Indexing")
	@ResponseBody
	public String toIndex() {
		System.out.println("indicizzazione...\n");
		this.indicizzatore.indicizzaCartella();
		return "Indicizzazione completata! \nSono stati indicizzati "+ this.indicizzatore.getCounterDocsIndexed()
		+ " documenti.\n" + "Con un numero complessivo di trascrizioni pari a: "+this.indicizzatore.getLastId(); 
	}
	
	//chiamata per il documento
	@RequestMapping(value="/toDoc")
	@ResponseBody
	public String toDoc() {
		return "prova.html";
	}
	
	
//	@RequestMapping("/prova")
//	@ResponseBody
//	public String stampa() {
//		String stringa= "Print try";
//		System.out.println(stringa);
//		return stringa;
//	}
	 
	//interrogazione
	@RequestMapping(value="/toFind", method= RequestMethod.POST)
	public String toFind(@RequestParam("search_input") String ricerca, Model model) throws Exception{
		this.interrogatore.ricercaFuzzy(ricerca);
		if(this.interrogatore.isValida()) {
			CreaRisultati risultati= this.interrogatore.getRisultati();
			List<RisultatoDoc> listaRisultati=risultati.getRisultatiDocumenti();
			System.out.println(risultati.getTotaleHits());
			for(RisultatoDoc ris: listaRisultati) {
				System.out.println(ris.getTitolo());
				System.out.println(ris.getScore());
				for(Coordinate coordinate: ris.getCoordinateP()) {
					System.out.println(coordinate.toString());
				}
			}
			Collections.sort(listaRisultati, new RisultatatoDocComparatore());
			model.addAttribute("listaRisultati", listaRisultati);
			model.addAttribute("hits",risultati.getTotaleHits());
			return "risultati.html";
		}
		else {
			model.addAttribute("errorCode", this.interrogatore.getErrorCode());
			return "searchPage.html";
		}
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
