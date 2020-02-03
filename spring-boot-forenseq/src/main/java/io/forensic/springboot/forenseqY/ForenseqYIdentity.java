package io.forensic.springboot.forenseqY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//Implementing Serializable for using this class as a key
@Embeddable
public class ForenseqYIdentity implements Serializable{

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

	@NotNull
	@Size(max=30)
	@Column(name = "Read_Count")
	private String read_count;
	
	public ForenseqYIdentity() {
		
	}

	public ForenseqYIdentity(String sampleYear, String sampleId, String locus, String allele, String read_count) {
		super();
		this.sampleYear = sampleYear;
		this.sampleId = sampleId;
		this.locus = locus;
		this.allele = allele;
		this.read_count = read_count;
	}

	public String getRead_count() {
		return read_count;
	}

	public void setRead_count(String read_count) {
		this.read_count = read_count;
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

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || this.getClass() != o.getClass()) return false; 
		
		//if object o isn't RazorIdentity
		ForenseqYIdentity that = (ForenseqYIdentity) o;
		
		if(!this.sampleId.equals(that.sampleId)) {
			return false;
		}
		if(!this.sampleYear.equals(that.sampleYear)) {
			return false;
		}
		if(!this.allele.equals(that.allele)) {
			return false;
		}
		return this.locus.equals(that.locus);
	}
}
