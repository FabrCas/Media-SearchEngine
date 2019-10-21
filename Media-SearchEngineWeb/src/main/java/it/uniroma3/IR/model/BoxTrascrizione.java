package it.uniroma3.IR.model;

/**
 * Classe che modella i box delle trascrizioni, quindi oltre alle informazioni 
 * base di un box, conosce la riga e le possibili trascrizioni al suo interno
 * 
 * @see Box
 **/

public class BoxTrascrizione extends Box{
	
	private String trascriptions;
	private int riga;
	
	public BoxTrascrizione() {
		super();
	}
	
	public BoxTrascrizione(Long x, Long y, Long width, Long height, String trascriptions, int riga) {
		super(x,y,width,height);
		this.trascriptions= trascriptions;
		this.riga=riga;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + riga;
		result = prime * result + ((trascriptions == null) ? 0 : trascriptions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoxTrascrizione other = (BoxTrascrizione) obj;
		if (riga != other.riga)
			return false;
		if (trascriptions == null) {
			if (other.trascriptions != null)
				return false;
		} else if (!trascriptions.equals(other.trascriptions))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Box [x=" + super.getX() + ", y=" + super.getY() + ", width=" + super.getWidth() +
				", height=" + super.getHeight() + ", trascriptions=" + this.trascriptions +", riga=" + this.riga + "]";
	}
	
	
	
	/*inizio getters & setters*/
	
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
	/*fine getters & setters*/
}
