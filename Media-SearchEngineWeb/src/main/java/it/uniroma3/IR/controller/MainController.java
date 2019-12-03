package it.uniroma3.IR.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.uniroma3.IR.comparatore.RisultatatoDocComparatore;
import it.uniroma3.IR.model.BoxTrascrizione;
import it.uniroma3.IR.model.RisultatoDoc;
import it.uniroma3.IR.service.CreaRisultati;
import it.uniroma3.IR.service.Indicizzatore;
import it.uniroma3.IR.service.Interrogatore;
import it.uniroma3.PrecisionRecall.CalcolatorePrecisionRecall;

@Controller
public class MainController {

	@Autowired
	private Indicizzatore indicizzatore;

	@Autowired
	private Interrogatore interrogatore;

	@Autowired
	private CalcolatorePrecisionRecall calcolatorePR;
	
	/*lista temporanea dei risulati, utili per la visualizzazione del documento*/
	private List<RisultatoDoc> listaRisultatiC;

	/**
	 * metodo per l'indicizzazione
	 */
	@RequestMapping(value="/Indexing")
	@ResponseBody
	public String toIndex() {
		System.out.println("indicizzazione...\n");
		this.indicizzatore.indicizzaCartella();
		return "Indicizzazione completata! \nSono stati indicizzati "+ this.indicizzatore.getCounterDocsIndexed()
		+ " documenti.\n" + "Con un numero complessivo di trascrizioni pari a: "+this.indicizzatore.getLastId(); 
	}

	/**
	 * metodo che effettua la ricerca 
	 * @param ricerca
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toFind", method= RequestMethod.POST)
	public String toFind(@RequestParam("search_input") String ricerca, Model model) throws Exception{
		this.interrogatore.ricercaFuzzy(ricerca);
		if(this.interrogatore.isValida()) {
			CreaRisultati risultati= this.interrogatore.getRisultati();
			List<RisultatoDoc> listaRisultati=risultati.getRisultatiDocumenti();
			
			/*stampe*/
			System.out.println(risultati.getTotaleHits());
			for(RisultatoDoc ris: listaRisultati) {
				System.out.println(ris.getTitolo());
				System.out.println(ris.getScore());
				for(BoxTrascrizione coordinate: ris.getBoxP()) {
					System.out.println(coordinate.toString());
				}
			}
			
			
			Collections.sort(listaRisultati, new RisultatatoDocComparatore());
			model.addAttribute("listaRisultati", listaRisultati);
			this.listaRisultatiC=listaRisultati;
			model.addAttribute("hits",risultati.getTotaleHits());
			model.addAttribute("documenti", risultati.getDocumentiConRiscontri());
			return "risultati.html";
		}
		else {
			model.addAttribute("errorCode", this.interrogatore.getErrorCode());
			return "searchPage.html";
		}
	}

	/**
	 * metodo che mostra la pagina del documento selezionato
	 */
	@RequestMapping(value = "/toDocumento/{titolo}" , method = RequestMethod.GET) ///{titolo}")
	public String getDocumento(@PathVariable ("titolo") String titolo,Model model) {
		System.out.println("titolo del documento selezionato è:"+ titolo );
		//ricerca risultati documento selezionato
			for(RisultatoDoc risultato: this.listaRisultatiC) {
				if(risultato.getTitolo().equals(titolo)) {
					model.addAttribute("titolo", risultato.getTitolo());
					model.addAttribute("nomeFile", risultato.getFile());
					model.addAttribute("coordinateD", risultato.getCoordinateD());
					model.addAttribute("boxP", risultato.getBoxP());
				}
			}
			return "documentoRicercato.html";
	}
	
	@RequestMapping(value = "/visualizzaDocumento/{titolo}" , method = RequestMethod.GET) ///{titolo}")
	public String visualizzaDocumento(@PathVariable ("titolo") String titolo,Model model) {
		model.addAttribute("titolo", titolo);
		return "documento.html";
	}
	
	

	/**
	 * prima pagina da visualizzare
	 */
	
	@RequestMapping(value = { "/", "/index"})
	public String index(Model model) {	
		this.Prova();
		return "searchPage.html";
	}

	/**
	 * ritorno alla home page
	 */
	@RequestMapping("/home")
	public String toHome(){	
		return "searchPage.html";
	}
	
	private void Prova() {
		System.out.println("prova!");
	}

}
