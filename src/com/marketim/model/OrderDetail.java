package com.marketim.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class OrderDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3222716109465103379L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@OneToOne
	private Product product = new Product();
	private String quantity;
	@ManyToOne
	@JoinColumn(name = "orderId")
	private OrderInfo order = new OrderInfo();
	
	

	public OrderDetail() {
		
	}

	public OrderDetail(Product product, String quantity, OrderInfo order) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.order = order;
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

	public OrderInfo getOrder() {
		return order;
	}

	public void setOrder(OrderInfo order) {
		this.order = order;
	}

}
