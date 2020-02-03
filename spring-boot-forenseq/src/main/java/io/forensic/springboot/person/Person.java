package io.forensic.springboot.person;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Person")
public class Person {

	@EmbeddedId
	private PersonIdentity id;
	
	private String PID;
	private String name;
	private String surname;
	private String gender;
	private String region;
	private String province;
	private String country;
	private int age;
	private String race;
	
	public Person() {
		
	}

	public Person(PersonIdentity id, String pID, String name, String surname, String gender, String region,
			String province, String country, int age, String race) {
		super();
		this.id = id;
		PID = pID;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.region = region;
		this.province = province;
		this.country = country;
		this.age = age;
		this.race = race;
	}

	public PersonIdentity getId() {
		return id;
	}

	public void setId(PersonIdentity id) {
		this.id = id;
	}

	public String getPID() {
		return PID;
	}

	public void setPID(String pID) {
		PID = pID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}
}
