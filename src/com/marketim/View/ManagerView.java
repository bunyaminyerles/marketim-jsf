package com.marketim.View;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.marketim.jpaFactory.JpaFactory;
import com.marketim.jpaFactory.impl.JpaFactoryImpl;
import com.marketim.model.Manager;

public class ManagerView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6325969947996403921L;
	private JpaFactory jpaFactory = new JpaFactoryImpl();
	EntityManager entityManager = jpaFactory.getEntityManager();
	EntityTransaction transaction = jpaFactory.getEntityTransaction();

	private Manager manager = new Manager();
	private boolean LoginQuery;

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public boolean isLoginQuery() {
		return LoginQuery;
	}

	public void setLoginQuery(boolean loginQuery) {
		LoginQuery = loginQuery;
	}

	public String loginControl() {
		System.out.println("deneme");
		try {
			Manager managerTmp = (Manager) entityManager.createQuery("from Manager m Where m.email = '"
					+ manager.getEmail() + "' and m.password = '" + manager.getPassword() + "'").getSingleResult();
			if (managerTmp != null) {
				manager = managerTmp;
				return "managerHomepage.xhtml?faces-redirect=true";
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
		manager = new Manager();
		return "managingLogin.xhtml?faces-redirect=true";
	}

	public String update() {
		transaction.begin();

		entityManager.merge(getManager());

		transaction.commit();

		return "managerHomepage.xhtml?faces-redirect=true";
	}
	
	public String check(String name) {
		if (name != "") {
			return "managerHomepage.xhtml";
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

}
