package it.uniroma3.IR.model;

public class Box {
	private String trascrizioni;
	private Coordinate coordinateBox;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinateBox == null) ? 0 : coordinateBox.hashCode());
		result = prime * result + ((trascrizioni == null) ? 0 : trascrizioni.hashCode());
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
		if (coordinateBox == null) {
			if (other.coordinateBox != null)
				return false;
		} else if (!coordinateBox.equals(other.coordinateBox))
			return false;
		if (trascrizioni == null) {
			if (other.trascrizioni != null)
				return false;
		} else if (!trascrizioni.equals(other.trascrizioni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Box [trascrizioni=" + trascrizioni + ", coordinateBox=" + coordinateBox + "]";
	}

	public Box() {}
	
	public String getTrascrizioni() {
		return trascrizioni;
	}
	public void setTrascrizioni(String trascrizioni) {
		this.trascrizioni = trascrizioni;
	}
	public Coordinate getCoordinateBox() {
		return coordinateBox;
	}
	public void setCoordinateBox(Coordinate coordinateBox) {
		this.coordinateBox = coordinateBox;
	}
	
	
}
