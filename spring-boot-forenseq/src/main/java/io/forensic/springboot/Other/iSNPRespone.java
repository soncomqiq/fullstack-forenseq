package io.forensic.springboot.Other;

public class iSNPRespone {
	private String locus;
	private String allele;
	private int amount;
	
	public iSNPRespone() {
		
	}

	public iSNPRespone(String locus, String allele, int amount) {
		super();
		this.locus = locus;
		this.allele = allele;
		this.amount = amount;
	}

	public String getLocus() {
		return locus;
	}

	public void setLocus(String locus) {
		this.locus = locus;
	}

	public String getAllele() {
		return allele;
	}

	public void setAllele(String allele) {
		this.allele = allele;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
