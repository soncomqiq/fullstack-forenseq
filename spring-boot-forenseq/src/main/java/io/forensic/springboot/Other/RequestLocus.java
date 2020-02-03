package io.forensic.springboot.Other;

public class RequestLocus {
	private String locus;
	private String allele;

	public RequestLocus() {

	}

	public RequestLocus(String locus, String allele) {
		super();
		this.locus = locus;
		this.allele = allele;
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
	
}

