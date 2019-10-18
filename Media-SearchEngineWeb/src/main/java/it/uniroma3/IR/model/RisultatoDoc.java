package it.uniroma3.IR.model;

/*
 * classe di modello per rappresentare i risultati per ogni documento
 * 
 * @see Box
 */


import java.util.ArrayList;
import java.util.List;

public class RisultatoDoc{
	
	private String titolo;
	private float score;
	private String file;
	private Box boxTesto;
	private List<BoxTrascrizione> boxP;
	
	public RisultatoDoc() {
		this.boxP= new ArrayList<BoxTrascrizione>();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boxP == null) ? 0 : boxP.hashCode());
		result = prime * result + ((boxTesto == null) ? 0 : boxTesto.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + Float.floatToIntBits(score);
		result = prime * result + ((titolo == null) ? 0 : titolo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RisultatoDoc other = (RisultatoDoc) obj;
		if (boxP == null) {
			if (other.boxP != null)
				return false;
		} else if (!boxP.equals(other.boxP))
			return false;
		if (boxTesto == null) {
			if (other.boxTesto != null)
				return false;
		} else if (!boxTesto.equals(other.boxTesto))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (Float.floatToIntBits(score) != Float.floatToIntBits(other.score))
			return false;
		if (titolo == null) {
			if (other.titolo != null)
				return false;
		} else if (!titolo.equals(other.titolo))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "RisultatoDoc [titolo=" + titolo + ", score=" + score + ", file=" + file + ", boxTesto=" + boxTesto
				+ ", boxP=" + boxP + "]";
	}
	
	/*inizio getters & setters*/
	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public float getScoreValue() {
		return this.score;
	}
	
	public String getScore() {
		return "Score:"+score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	public void addScore(float score) {
		this.score += score;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String nomeFile) {
		this.file = nomeFile;
	}
	
	public List<BoxTrascrizione> getBoxP() {
		return this.boxP;
	}

	public void addBoxP(BoxTrascrizione box) {
		this.boxP.add(box);
	}
	
	public void setBoxP(List<BoxTrascrizione> boxP) {
		this.boxP = boxP;
	}

	public Box getCoordinateD() {
		return boxTesto;
	}

	public void setCoordinateD(Box coordinateT) {
		this.boxTesto = coordinateT;
	}
	
	/*fine getters & setters*/
	
}
