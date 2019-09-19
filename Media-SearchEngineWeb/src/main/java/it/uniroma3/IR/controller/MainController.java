package it.uniroma3.IR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.IR.service.CreaRisultati;
import it.uniroma3.IR.service.Indicizzatore;


@Controller
public class MainController {
	
	@Autowired
	private Indicizzatore indicizzatore;
	
	//private CreaRisultati creaRisultati;
	
	//indicizzazione
	@RequestMapping(value="/toIndex", method= RequestMethod.POST)
	public void toIndex() {
		System.out.println("indicizzazione...\n");
		this.indicizzatore.indicizzaCartella();
	}
	
	//interrogazione
	@RequestMapping(value="/toFind", method= RequestMethod.POST)
	public String toFind(@RequestParam("search_input") String ricerca, Model model) throws Exception{
		return ricerca;
	}
	
	
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {
		
		return "searchPage.html";
	}
	
	
	@RequestMapping("/home")
	public String toHome(){
		return "searchPage.html";
	}

}
