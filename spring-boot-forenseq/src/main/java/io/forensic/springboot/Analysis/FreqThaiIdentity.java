package io.forensic.springboot.Analysis;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class FreqThaiIdentity implements Serializable {
	
	@NotNull
	@Size(max = 100)
	@Column(name = "country")
	private String country;

	@NotNull
	@Size(max = 50)
	@Column(name = "locus")
	private String locus;

	@NotNull
	@Column(name = "allele")
	private float allele;
	
	public FreqThaiIdentity() {
		
	}

	public FreqThaiIdentity(@NotNull @Size(max = 100) String country, @NotNull @Size(max = 50) String locus,
			@NotNull float allele) {
		super();
		this.country = country;
		this.locus = locus;
		this.allele = allele;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLocus() {
		return locus;
	}

	public void setLocus(String locus) {
		this.locus = locus;
	}

	public float getAllele() {
		return allele;
	}

	public void setAllele(float allele) {
		this.allele = allele;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || this.getClass() != o.getClass()) return false; 
		
		//if object o isn't RazorIdentity
		FreqThaiIdentity that = (FreqThaiIdentity) o;
		
		if(!this.locus.equals(that.locus)) {
			return false;
		}
		if(!this.country.equals(that.country)) {
			return false;
		}
		return this.allele == (that.allele);
	}
}
