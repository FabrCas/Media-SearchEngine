package it.uniroma3.IR.comparatore;
import java.util.Comparator;
import it.uniroma3.IR.model.RisultatoDoc;
/**
 * classe comparatrice che permette l'ordinamento dei risultati in base
 * allo score per ogni documento.
 * 
 * @see RisultatoDoc
 **/

public class RisultatatoDocComparatore implements Comparator<RisultatoDoc>{

	@Override
	public int compare(RisultatoDoc o1, RisultatoDoc o2) {
		int diff= (int)( -o1.getScoreValue() + o2.getScoreValue());
		if(diff!=0) {
			return diff;
		}
		return o1.getTitolo().compareTo(o2.getTitolo());
	}

}
