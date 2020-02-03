package io.forensic.springboot.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class PersonIdentity implements Serializable{

	@NotNull
	@Size(max=20)
	@Column(name = "Sample_Year")
	private String sampleYear;
	
	@NotNull
	@Size(max=20)
	@Column(name = "Sample_ID")
	private String sampleId;
	
	public PersonIdentity() {
		
	}
	
	public PersonIdentity(String sampleYear,String sampleId) {
		this.sampleId = sampleId;
		this.sampleYear = sampleYear;
	}
	
	public String getsampleYear() {
		return sampleYear;
	}

	public void setsampleYear(String sample_Year) {
		sampleYear = sample_Year;
	}

	public String getsampleId() {
		return sampleId;
	}

	public void setsampleId(String sample_ID) {
		sampleId = sample_ID;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || this.getClass() != o.getClass()) return false; 
		
		//if object o isn't RazorIdentity
		PersonIdentity that = (PersonIdentity) o;
		
		if(!this.sampleId.equals(that.sampleId)) {
			return false;
		}
		
		return this.sampleYear.equals(that.sampleYear);
	}
}
