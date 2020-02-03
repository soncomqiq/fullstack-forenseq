package io.forensic.springboot.STRLocusInfo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//Implementing Serializable for using this class as a key
@Embeddable
public class STRLocusInfoIdentity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	@Column(name = "Genotype")
	private String genotype;
	
	@NotNull
	@Column(name = "froms")
	private String from;
	
	public STRLocusInfoIdentity() {
		
	}

	public STRLocusInfoIdentity(String sampleYear, String sampleId, String locus, String genotype, String from) {
		super();
		this.sampleYear = sampleYear;
		this.sampleId = sampleId;
		this.locus = locus;
		this.genotype = genotype;
		this.from = from;
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

	public String getGenotype() {
		return genotype;
	}

	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || this.getClass() != o.getClass()) return false; 
		
		//if object o isn't RazorIdentity
		STRLocusInfoIdentity that = (STRLocusInfoIdentity) o;
		
		if(!this.sampleId.equals(that.sampleId)) {
			return false;
		}
		if(!this.sampleYear.equals(that.sampleYear)) {
			return false;
		}
		if(!this.genotype.equals(that.genotype)) {
			return false;
		}
		if(!this.from.equals(that.from)) {
			return false;
		}
		return this.locus.equals(that.locus);
	}
}
