package it.uniroma3.IR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.IR.service.CreaRisultati;
import it.uniroma3.IR.service.Indicizzatore;


@Controller
public class MainController {
	
	@Autowired
	private Indicizzatore indicizzatore;
	@Autowired
	private CreaRisultati creaRisultati;
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {
		
		this.indicizzatore.indicizzaCartella(creaRisultati);
		return "searchPage.html";
	}

}
