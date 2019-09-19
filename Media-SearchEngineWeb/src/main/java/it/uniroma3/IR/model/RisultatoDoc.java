package it.uniroma3.IR.model;

public class RisultatoDoc {
	
	private String titolo;
	private String score;
	private String file;
	
	public RisultatoDoc() {
	}
	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String nomeFile) {
		this.file = nomeFile;
	}
	
}
