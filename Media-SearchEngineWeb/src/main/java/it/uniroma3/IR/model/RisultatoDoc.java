package it.uniroma3.IR.model;

import java.util.ArrayList;
import java.util.List;

public class RisultatoDoc{
	
	private String titolo;
	private float score;
	private String file;
	private List<Coordinate> coordinate;
	
	public RisultatoDoc() {
		this.coordinate= new ArrayList<Coordinate>();
	}
	
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
	
	public List<Coordinate> getCoordinate() {
		return coordinate;
	}

	public void addCoordinate(Coordinate coordinata) {
		this.coordinate.add(coordinata);
	}


	
}
