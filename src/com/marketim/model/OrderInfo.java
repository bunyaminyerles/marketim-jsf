package com.marketim.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class OrderInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4394041070814647753L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	private String orderState;
	private String totalPayment;
	@OneToOne
	private Client client = new Client();
	@OneToOne
	private Courier courier = new Courier();
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> product = new ArrayList<OrderDetail>();
	@Version
	private Long version;
	

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Courier getCourier() {
		return courier;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}

	public List<OrderDetail> getProduct() {
		return product;
	}

	public void setProduct(List<OrderDetail> product) {
		this.product = product;
	}
	
	
	public String getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(String totalPayment) {
		this.totalPayment = totalPayment;
	}

	public void addProductList(OrderDetail product) {
		this.product.add(product);
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
}
