package io.forensic.springboot.Analysis;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Freq")
public class FreqThai {
	
	@EmbeddedId
	private FreqThaiIdentity id;
	
	@NotNull
	@Column(name = "freq")
	private float freq;

	public FreqThai() {
		
	}

	public FreqThai(FreqThaiIdentity id, @NotNull float freq) {
		super();
		this.id = id;
		this.freq = freq;
	}

	public FreqThaiIdentity getId() {
		return id;
	}

	public void setId(FreqThaiIdentity id) {
		this.id = id;
	}

	public float getFreq() {
		return freq;
	}

	public void setFreq(float freq) {
		this.freq = freq;
	}
	
}
