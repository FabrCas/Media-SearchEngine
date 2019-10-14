package it.uniroma3.IR.model;
/*
 * trascriptions e riga sono attributi non obbligatori,
 * come nel caso in cui questo classe modelli il box del testo dell'intero
 * documento, dove il campo trascrizioni non è necessario e nemmeno quello
 * della riga , quindi sono omessi 
 * possibile idea: superclasse->box 
 *                 sottoclasse->box trascrizione
 * dal toString()
 */
public class Box {
	private Long x;
	private Long y;
	private Long width;
	private Long height;
	
	/*variabili non obbligatorie*/
	private String trascriptions;
	private int riga;
	
	public Box(Long x, Long y, Long width, Long height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Box() {
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((trascriptions == null) ? 0 : trascriptions.hashCode());
		result = prime * result + ((width == null) ? 0 : width.hashCode());
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
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
		Box other = (Box) obj;
		if (height == null) {
			if (other.height != null)
				return false;
		} else if (!height.equals(other.height))
			return false;
		if (width == null) {
			if (other.width != null)
				return false;
		} else if (!width.equals(other.width))
			return false;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}
	
	

	@Override
	public String toString() {
		return "Box [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}

	
	/*setter & getters obbligatori*/
	public Long getX() {
		return x;
	}
	public void setX(Long x) {
		this.x = x;
	}
	public Long getY() {
		return y;
	}
	public void setY(Long y) {
		this.y = y;
	}
	public Long getWidth() {
		return width;
	}
	public void setWidth(Long width) {
		this.width = width;
	}
	public Long getHeight() {
		return height;
	}
	public void setHeight(Long height) {
		this.height = height;
	}
	
	
	/*setter & getters obbligatori*/
	public String getTrascriptions() {
		return trascriptions;
	}

	public void setTrascriptions(String trascriptions) {
		this.trascriptions = trascriptions;
	}
	
	public int getRiga() {
		return riga;
	}

	public void setRiga(int riga) {
		this.riga = riga;
	}

}