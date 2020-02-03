package io.forensic.springboot.Other;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AutosomalKit")
public class AutosomalKit {

	@EmbeddedId
	private KitIdentity id;

	public AutosomalKit() {
		
	}

	public AutosomalKit(KitIdentity id) {
		super();
		this.id = id;
	}

	public KitIdentity getId() {
		return id;
	}

	public void setId(KitIdentity id) {
		this.id = id;
	}
	
}
