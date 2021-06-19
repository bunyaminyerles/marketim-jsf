package com.marketim.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1732290507590332742L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String productName;
	private String pcsUnit;
	private String salePrice;
	private String costOfProduct;
	private String stock;
	@Lob
	private byte[] photo;
	@OneToOne(fetch = FetchType.EAGER)
	private Category category = new Category();

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPcsUnit() {
		return pcsUnit;
	}

	public void setPcsUnit(String pcsUnit) {
		this.pcsUnit = pcsUnit;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getCostOfProduct() {
		return costOfProduct;
	}

	public void setCostOfProduct(String costOfProduct) {
		this.costOfProduct = costOfProduct;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	
	
}
