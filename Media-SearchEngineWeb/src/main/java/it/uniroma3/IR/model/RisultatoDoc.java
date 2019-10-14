package it.uniroma3.IR.model;

import java.util.ArrayList;
import java.util.List;

public class RisultatoDoc{
	
	private String titolo;
	private float score;
	private String file;
	private Box coordinateD;
	private List<Box> boxP;
	
	public RisultatoDoc() {
		this.boxP= new ArrayList<Box>();
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
	
	public List<Box> getBoxP() {
		return this.boxP;
	}

	public void addBoxP(Box box) {
		this.boxP.add(box);
	}
	
	public void setBoxP(List<Box> boxP) {
		this.boxP = boxP;
	}

	public Box getCoordinateD() {
		return coordinateD;
	}

	public void setCoordinateD(Box coordinateT) {
		this.coordinateD = coordinateT;
	}

	
}
