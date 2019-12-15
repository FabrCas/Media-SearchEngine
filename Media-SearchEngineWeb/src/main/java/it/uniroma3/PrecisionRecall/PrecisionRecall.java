package it.uniroma3.PrecisionRecall;

public class PrecisionRecall {

	public double precision;
	public double recall;
	public String query;

	public PrecisionRecall(double precision, double recall, String query) {
		this.precision = precision;
		this.recall = recall;
		this.query= query;
	}
	
	public double getPrecision() {
		return precision;
	}

	public double getRecall() {
		return recall;
	}
	
	public String getQuery() {
		return this.query;
	}

}