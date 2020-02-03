package io.forensic.springboot.Other;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//Implementing Serializable for using this class as a key
@Embeddable
public class KitIdentity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max=40)
	@Column(name = "kit")
	private String kit;
	
	@NotNull
	@Size(max=40)
	@Column(name = "locus")
	private String locus;
	
	public KitIdentity() {
		
	}

	public KitIdentity(String kit, String locus) {
		super();
		this.kit = kit;
		this.locus = locus;
	}

	public String getKit() {
		return kit;
	}

	public void setKit(String kit) {
		this.kit = kit;
	}

	public String getLocus() {
		return locus;
	}

	public void setLocus(String locus) {
		this.locus = locus;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || this.getClass() != o.getClass()) return false; 
		
		//if object o isn't RazorIdentity
		KitIdentity that = (KitIdentity) o;
		
		if(!this.locus.equals(that.locus)) {
			return false;
		}
		return this.kit.equals(that.kit);
	}
}
