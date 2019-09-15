package it.uniroma3.IR.model;

import java.util.Map;

public class Pagina {
	
	private Long id;
	private Map<String,Coordinate> mappaturaParole;
	
	public Pagina(Long id, Map<String, Coordinate> mappaturaParola) {
		this.id = id;
		this.mappaturaParole = mappaturaParola;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Map<String, Coordinate> getMappaturaParola() {
		return mappaturaParole;
	}
	public void setMappaturaParola(Map<String, Coordinate> mappaturaParola) {
		this.mappaturaParole = mappaturaParola;
	}
	
	

}
