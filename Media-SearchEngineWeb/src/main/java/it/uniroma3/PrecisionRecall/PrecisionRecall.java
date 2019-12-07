package it.uniroma3.PrecisionRecall;

public class PrecisionRecall {

	public double precision;
	public double recall;

	public PrecisionRecall(double precision, double recall) {
		this.precision = precision;
		this.recall = recall;
	}
	
	public double getPrecision() {
		return precision;
	}

	public double getRecall() {
		return recall;
	}

}