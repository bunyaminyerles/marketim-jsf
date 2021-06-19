package com.marketim.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Manager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1026764799659396166L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String telephoneNumber;
	private String email;
	private String password;
	private String name;
	private String surName;

	public Manager() {

	}

	public Manager(String telephoneNumber, String email, String password, String name, String surName) {
		super();
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.password = password;
		this.name = name;
		this.surName = surName;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

}
