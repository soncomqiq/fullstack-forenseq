package io.forensic.springboot.STRLocusInfo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "STR_LOCUS_INFO")
public class STRLocusInfo {

	@EmbeddedId
	private STRLocusInfoIdentity id;

	@Column(name = "_Type")
	private String type;

	@Column(name = "QCIndicate")
	private String QCIndicate;

	public STRLocusInfo() {

	}

	public STRLocusInfo(STRLocusInfoIdentity id, String type, String QCIndicate) {
		super();
		this.id = id;
		this.type = type;
		this.QCIndicate = QCIndicate;
	}

	public String getQCIndicate() {
		return QCIndicate;
	}

	public void setQCIndicate(String qCIndicate) {
		QCIndicate = qCIndicate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public STRLocusInfoIdentity getId() {
		return id;
	}

	public void setId(STRLocusInfoIdentity id) {
		this.id = id;
	}

}
