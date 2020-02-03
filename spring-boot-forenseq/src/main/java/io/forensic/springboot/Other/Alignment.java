package io.forensic.springboot.Other;

public class Alignment {
	
	private String sampleYear;
	
	private String sampleId;
	
	private String genotype;
	
	private String locus;
	
	private String allele;
	
	private String readCount;
	
	private String type;
	
	private String sequence;
	
	private String seqAlignment;
	
	public Alignment() {
		
	}

	public Alignment(String sampleYear, String sampleId, String genotype, String locus, String allele, String readCount,
			String type, String sequence, String seqAlignment) {
		super();
		this.sampleYear = sampleYear;
		this.sampleId = sampleId;
		this.genotype = genotype;
		this.locus = locus;
		this.allele = allele;
		this.readCount = readCount;
		this.type = type;
		this.sequence = sequence;
		this.seqAlignment = seqAlignment;
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

	public String getGenotype() {
		return genotype;
	}

	public void setGenotype(String genotype) {
		this.genotype = genotype;
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

	public String getReadCount() {
		return readCount;
	}

	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getSeqAlignment() {
		return seqAlignment;
	}

	public void setSeqAlignment(String seqAlignment) {
		this.seqAlignment = seqAlignment;
	}
}
