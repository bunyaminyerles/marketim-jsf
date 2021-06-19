package com.marketim.View;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.marketim.jpaFactory.JpaFactory;
import com.marketim.jpaFactory.impl.JpaFactoryImpl;
import com.marketim.model.OrderDetail;

public class OrderDetailView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7896710724747229087L;
	private JpaFactory jpaFactory = new JpaFactoryImpl();
	EntityManager entityManager = jpaFactory.getEntityManager();
	EntityTransaction transaction = jpaFactory.getEntityTransaction();

	private OrderDetail productForOrder = new OrderDetail();

	public OrderDetail getProductForOrder() {
		return productForOrder;
	}

	public void setProductForOrder(OrderDetail productForOrder) {
		this.productForOrder = productForOrder;
	}

	public void add(List<OrderDetail> product) {

		transaction.begin();
		ListIterator<OrderDetail> iterator = product.listIterator();

		while (iterator.hasNext()) {
			productForOrder = iterator.next();
			entityManager.persist(getProductForOrder());
		}

		transaction.commit();
		productForOrder = new OrderDetail();
	}

	public List<OrderDetail> list(Long id) {	
		try {
			@SuppressWarnings("unchecked")
			List<OrderDetail> orderDetails = entityManager.createQuery("from OrderDetail WHERE orderId=" + id)
					.getResultList();

			if (orderDetails != null) {
				return orderDetails;
			} else {
				return null;
			}
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hatalý",
						"Detaylar bulunamadý");
				FacesContext.getCurrentInstance().addMessage(null, message);

				return null;
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.toString());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		}
}
	
}
