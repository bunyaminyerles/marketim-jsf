package com.marketim.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
public class Basket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9184697828650959624L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@OneToOne(fetch = FetchType.EAGER)
	private Product product = new Product();
	private String quantity;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "clientId")
	private Client client = new Client();
	@Version
	private Long version;
	
	public Basket() {
		super();
	}
	public Basket(Product product, String quantity, Client client) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.client = client;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
}
