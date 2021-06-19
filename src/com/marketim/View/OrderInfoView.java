package com.marketim.View;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.marketim.jpaFactory.JpaFactory;
import com.marketim.jpaFactory.impl.JpaFactoryImpl;
import com.marketim.model.Client;
import com.marketim.model.Courier;
import com.marketim.model.OrderInfo;

public class OrderInfoView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -425114644451434751L;
	private JpaFactory jpaFactory = new JpaFactoryImpl();
	EntityManager entityManager = jpaFactory.getEntityManager();
	EntityTransaction transaction = jpaFactory.getEntityTransaction();

	private OrderInfo order = new OrderInfo();

	public OrderInfo getOrder() {
		return order;
	}

	public void setOrder(OrderInfo order) {
		this.order = order;
	}

	public void add() {
		entityManager.clear();
		transaction.begin();
		entityManager.persist(getOrder());
		entityManager.flush();
		transaction.commit();
		order = new OrderInfo();
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderInfo> findOrders(Client client) {
		try {
			List<OrderInfo> orders = entityManager.createQuery("from OrderInfo WHERE client_Id=" + client.getId()+" ORDER BY Id DESC")
					.getResultList();

			if (orders != null) {
				return orders;
			} else {
				return null;
			}
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Daha önceden verilmiþ sipariþiniz yok");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.toString());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfo> findOrders() {
		try {
			List<OrderInfo> orders = entityManager.createQuery("from OrderInfo Where courier_Id IS NULL ORDER BY Id")
					.getResultList();

			if (orders != null) {
				return orders;
			} else {
				return null;
			}
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Bekleyen sipariþ yok");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.toString());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		}
	}
	
	public OrderInfo find(Long id) {
		OrderInfo tmpOrder = entityManager.find(OrderInfo.class, id);
		if (tmpOrder != null) {
			return tmpOrder;
		}
		return null;
	}
	
	public String orderSelection(Long id) {
		order = find(id);
		return "orderDetails.xhtml?faces-redirect=true";
	}
	
	public void courierChose(Long id,Courier courier) {
		transaction.begin();
		order = find(id);
		order.setCourier(courier);
		order.setOrderState("Hazýrlanýyor");
		entityManager.merge(order);
		transaction.commit();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Sipariþ alýndý.");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void stateUpdate(Long id,String state) {
		transaction.begin();
		order = find(id);
		order.setOrderState(state);
		entityManager.merge(order);
		transaction.commit();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", state);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public OrderInfo orderControl(Courier courier) {
		try {
			OrderInfo orderTmp = (OrderInfo) entityManager.createQuery("from OrderInfo  Where courier_Id = " + courier.getId()+" AND orderState <> 'Teslim Edildi' AND orderState <> 'Ýptal Edildi'").getSingleResult();
			return orderTmp;
		} catch (Exception e) {
			return new OrderInfo();
		}	
		
	}
	
	public List<OrderInfo> list() {

		try {
			@SuppressWarnings("unchecked")
			List<OrderInfo> orders = entityManager.createQuery("from OrderInfo ORDER BY Id DESC").getResultList();

			if (orders != null) {
				return orders;
			} else {
				return null;
			}
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hatalý", "Sipariþ bulunamadý");
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
