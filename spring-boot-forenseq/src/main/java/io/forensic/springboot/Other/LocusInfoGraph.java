package io.forensic.springboot.Other;

public class LocusInfoGraph {
	private String allele;
	private String amount;
	
	public LocusInfoGraph() {
		
	}

	public LocusInfoGraph(String allele, String amount) {
		super();
		this.allele = allele;
		this.amount = amount;
	}

	public String getAllele() {
		return allele;
	}

	public void setAllele(String allele) {
		this.allele = allele;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
