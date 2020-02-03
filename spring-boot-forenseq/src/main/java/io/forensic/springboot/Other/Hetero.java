package io.forensic.springboot.Other;

public class Hetero {
	private String locus;
	private String total;
	private String hetero;
	
	public Hetero() {
		
	}

	public Hetero(String locus, String total, String hetero) {
		super();
		this.locus = locus;
		this.total = total;
		this.hetero = hetero;
	}

	public String getLocus() {
		return locus;
	}

	public void setLocus(String locus) {
		this.locus = locus;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getHetero() {
		return hetero;
	}

	public void setHetero(String hetero) {
		this.hetero = hetero;
	}
	
}
