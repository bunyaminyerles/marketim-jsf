package com.marketim.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2973374020559037771L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String telephoneNumber;
	private String email;
	private String password;
	private String name;
	private String surName;
	@Embedded
	private Adress adress = new Adress();
	@OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
	private List<Basket> product = new ArrayList<Basket>();


	public Client() {

	}

	public Client(String telephoneNumber, String email, String password, String name, String surName, Adress adress) {
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.password = password;
		this.name = name;
		this.surName = surName;
		this.adress = adress;
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

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public List<Basket> getBasket() {
		return product;
	}

	public void setBasket(List<Basket> product) {
		this.product = product;
	}
	
	public void setProduct(List<Basket> product) {
		this.product = product;
	}
	
	public void addProductList(Basket product) {
		this.product.add(product);
	}
}
