package io.forensic.springboot.iSNPs;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// Name In JSON Object Spring brings from methods' name

@Entity
@Table(name = "iSNPData")
public class iSNPs {

	@EmbeddedId
	private iSNPsIdentity iSNPsIdentity;

	@Column(name = "_Type")
	private String type;
	
	@Column(name = "Genotype")
	private String genotype;
	
	@Column(name = "Read_Count")
	private String read_count;

	public iSNPs() {

	}

	public iSNPs(iSNPsIdentity iSNPsIdentity, String type, String genotype, String read_count) {
		super();
		this.iSNPsIdentity = iSNPsIdentity;
		this.type = type;
		this.genotype = genotype;
		this.read_count = read_count;
	}

	public iSNPsIdentity getForenseqIdentity() {
		return iSNPsIdentity;
	}

	public void setForenseqIdentity(iSNPsIdentity iSNPsIdentity) {
		this.iSNPsIdentity = iSNPsIdentity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGenotype() {
		return genotype;
	}

	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}

	public String getRead_count() {
		return read_count;
	}

	public void setRead_count(String read_count) {
		this.read_count = read_count;
	}

}
