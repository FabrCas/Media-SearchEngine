package it.uniroma3.IR.model;

import java.util.List;
import java.util.Map;

public class Pagina {
	
	private Long id;
	private Map<String,List<Coordinate>> mappaturaParole;
	
	public Pagina(Long id, Map<String, List<Coordinate>> mappaturaParola) {
		this.id = id;
		this.mappaturaParole = mappaturaParola;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Map<String, List<Coordinate>> getMappaturaParola() {
		return mappaturaParole;
	}
	public void setMappaturaParola(Map<String, List<Coordinate>> mappaturaParola) {
		this.mappaturaParole = mappaturaParola;
	}
	
	

}
