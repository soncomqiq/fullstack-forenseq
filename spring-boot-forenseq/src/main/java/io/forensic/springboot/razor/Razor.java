package io.forensic.springboot.razor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Razor")
public class Razor {

	@EmbeddedId
	private RazorIdentity id;

	private String genotype;
	private int sequence_Length;
	private String sequence;

	public Razor() {

	}
	
	public Razor(RazorIdentity id, String genotype, int sequence_Length, String sequence) {
		this.id = id;
		this.genotype = genotype;
		this.sequence_Length = sequence_Length;
		this.sequence = sequence;
	}

	public RazorIdentity getId() {
		return id;
	}

	public void setId(RazorIdentity id) {
		this.id = id;
	}

	public String getGenotype() {
		return genotype;
	}

	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}

	public int getSequence_Length() {
		return sequence_Length;
	}

	public void setSequence_Length(int sequence_Length) {
		this.sequence_Length = sequence_Length;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
