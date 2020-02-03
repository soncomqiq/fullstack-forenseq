package io.forensic.springboot.iSNPs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class iSNPsIdentity implements Serializable {
	
	@NotNull
	@Size(max=20)
	@Column(name = "Sample_Year")
	private String sampleYear;
	
	@NotNull
	@Size(max=20)
	@Column(name = "Sample_ID")
	private String sampleId;
	
	@NotNull
	@Size(max=40)
	@Column(name = "Locus")
	private String locus;
	
	@NotNull
	@Size(max=30)
	@Column(name = "Allele")
	private String allele;
	
	public iSNPsIdentity() {
		
	}
	
	public iSNPsIdentity(@NotNull @Size(max = 20) String sampleYear, @NotNull @Size(max = 20) String sampleId,
			@NotNull @Size(max = 40) String locus, @NotNull @Size(max = 30) String allele) {
		super();
		this.sampleYear = sampleYear;
		this.sampleId = sampleId;
		this.locus = locus;
		this.allele = allele;
	}

	public String getSampleYear() {
		return sampleYear;
	}

	public void setSampleYear(String sampleYear) {
		this.sampleYear = sampleYear;
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
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
