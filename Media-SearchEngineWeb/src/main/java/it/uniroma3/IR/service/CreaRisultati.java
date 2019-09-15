package it.uniroma3.IR.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.uniroma3.IR.model.Pagina;

@Component
public class CreaRisultati {
	
	private List<Pagina> pagineDellaRicerca;
	
	public CreaRisultati() {
		this.pagineDellaRicerca= new ArrayList<Pagina>();
	}
	
	public void aggiungiPagina (Pagina p) {
		this.pagineDellaRicerca.add(p);
	}
	
	//TODO
}
