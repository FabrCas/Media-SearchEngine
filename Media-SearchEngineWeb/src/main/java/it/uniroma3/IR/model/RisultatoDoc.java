package it.uniroma3.IR.model;

import java.util.ArrayList;
import java.util.List;

public class RisultatoDoc{
	
	private String titolo;
	private float score;
	private String file;
	private Coordinate coordinateD;
	private List<Coordinate> coordinateP;
	
	public RisultatoDoc() {
		this.coordinateP= new ArrayList<Coordinate>();
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
	
	public List<Coordinate> getCoordinateP() {
		return coordinateP;
	}

	public void addCoordinateP(Coordinate coordinata) {
		this.coordinateP.add(coordinata);
	}
	
	public void setCoordinateP(List<Coordinate> coordinateP) {
		this.coordinateP = coordinateP;
	}

	public Coordinate getCoordinateD() {
		return coordinateD;
	}

	public void setCoordinateD(Coordinate coordinateT) {
		this.coordinateD = coordinateT;
	}

	
}
