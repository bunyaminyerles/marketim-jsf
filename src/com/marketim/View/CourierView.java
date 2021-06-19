package com.marketim.View;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.marketim.jpaFactory.JpaFactory;
import com.marketim.jpaFactory.impl.JpaFactoryImpl;
import com.marketim.model.Courier;

public class CourierView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2375056014899212089L;
	private JpaFactory jpaFactory = new JpaFactoryImpl();
	EntityManager entityManager = jpaFactory.getEntityManager();
	EntityTransaction transaction = jpaFactory.getEntityTransaction();

	private Courier courier = new Courier();

	public Courier getCourier() {
		return courier;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}

	public String loginControl() {
		System.out.println("deneme");
		try {
			Courier courierTmp = (Courier) entityManager.createQuery("from Courier c Where c.email = '"
					+ courier.getEmail() + "' and c.password = '" + courier.getPassword() + "'").getSingleResult();
			if (courierTmp != null) {
				courier = courierTmp;
				return "courierHomepage.xhtml?faces-redirect=true";
			} else {
				return "";
			}

		} catch (Exception e) {

			if (e.getMessage().equals("No entity found for query")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hatalý",
						"E-posta veya þifre hatalý");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return "";
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.toString());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return "";
			}
		}

	}

	public String logoutControl() {
		courier = new Courier();
		return "managingLogin.xhtml?faces-redirect=true";
	}

	public void add() {
		transaction.begin();

		entityManager.persist(getCourier());

		transaction.commit();

		courier = new Courier();
	}

	public void remove() {
		try {
			transaction.begin();

			entityManager.remove(getCourier());

			transaction.commit();

			courier = new Courier();
			FacesMessage message = new FacesMessage("Kurye silindi");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {

			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public String update() {
		transaction.begin();

		entityManager.merge(getCourier());

		transaction.commit();

		return "courierHomepage.xhtml?faces-redirect=true";
	}

	public void update2() {
		transaction.begin();

		entityManager.merge(getCourier());

		transaction.commit();

		courier = new Courier();
	}

	public void openNew() {
		courier = new Courier();
		courier.setName(" ");
	}

	public Courier find(Long id) {

		Courier tmpCourier = entityManager.find(Courier.class, id);
		if (tmpCourier != null) {
			return tmpCourier;
		}
		return null;
	}

	public List<Courier> list() {
		try {
			@SuppressWarnings("unchecked")
			List<Courier> couriers = entityManager.createQuery("from Courier").getResultList();

			if (couriers != null) {
				return couriers;
			} else {
				return null;
			}
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hatalý", "Kurye bulunamadý");
				FacesContext.getCurrentInstance().addMessage(null, message);

				return null;
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.toString());
				FacesContext.getCurrentInstance().addMessage(null, message);

				return null;
			}
		}
	}

	public String check(String name) {
		if (name != "") {
			return "courierHomepage.xhtml";
		} else {
			return "";
		}

	}

	public String checkNull(String name) {
		if (name == "") {
			return "managingLogin.xhtml";
		} else {
			return "";
		}

	}

	public void save() {
		if (courier.getId() == null) {
			add();
			FacesMessage message = new FacesMessage("Kurye eklendi");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			update2();
			FacesMessage message = new FacesMessage("Kurye güncellendi");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

}
