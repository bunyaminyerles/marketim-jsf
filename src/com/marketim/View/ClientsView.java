package com.marketim.View;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.marketim.jpaFactory.JpaFactory;
import com.marketim.jpaFactory.impl.JpaFactoryImpl;
import com.marketim.model.Adress;
import com.marketim.model.Basket;
import com.marketim.model.Client;

public class ClientsView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7014099463850397173L;
	private JpaFactory jpaFactory = new JpaFactoryImpl();
	EntityManager entityManager = jpaFactory.getEntityManager();
	EntityTransaction transaction = jpaFactory.getEntityTransaction();

	private Client client = new Client();
	private Client client2 = new Client();
	private Basket basket = new Basket();

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient2() {
		return client2;
	}

	public void setClient2(Client client2) {
		this.client2 = client2;
	}
	
	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public String loginControl() {
		try {
			Client clientTmp = (Client) entityManager.createQuery("from Client c Where c.email = '" + client.getEmail()
					+ "' and c.password = '" + client.getPassword() + "'").getSingleResult();
			if (clientTmp != null) {
				client = clientTmp;
				return "homepage.xhtml?faces-redirect=true";
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
		client = new Client();
		return "homepage.xhtml?faces-redirect=true";
	}

	public String add() {
		transaction.begin();

		entityManager.persist(getClient2());

		transaction.commit();

		client2 = new Client();

		return "login.xhtml?faces-redirect=true";
	}
	
	public void remove() {
		transaction.begin();

		entityManager.remove(getClient());

		transaction.commit();

		client = new Client();

	}

	public String update() {
		transaction.begin();
		Adress adress = new Adress(client2.getAdress().getCity(),client2.getAdress().getDistrict(),
				client.getAdress().getNeighborhood(),client.getAdress().getStreet(),
				client.getAdress().getBuildingNumber(),client.getAdress().getFloorNumber(),
				client.getAdress().getApartmentNumber());
		client2 = new Client();
		client.setAdress(adress);
		entityManager.merge(getClient());
		transaction.commit();
		
		return "homepage.xhtml?faces-redirect=true";
	}

	public Client find(Long id) {

		Client tmpClient = entityManager.find(Client.class, id);
		if (tmpClient != null) {
			return tmpClient;
		}
		return null;
	}

	public List<Client> list() {
		
		return null;
	}

	public String check(String name) {
		if (name != "") {
			return "homepage.xhtml";
		} else {
			return "";
		}

	}
	
	public String checkNull(String name) {
		if (name == "") {
			return "homepage.xhtml";
		} else {
			return "";
		}

	}

	private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
	private Map<String, String> cities;
	private Map<String, String> districties;

	@PostConstruct
	public void init() {
		cities = new HashMap<String, String>();
		cities.put("Ankara", "Ankara");
		cities.put("Gaziantep", "Gaziantep");
		cities.put("Ýstanbul", "Ýstanbul");

		Map<String, String> map = new HashMap<String, String>();
		map.put("Çankaya", "Çankaya");
		map.put("Yenimahalle", "Yenimahalle");
		map.put("Elmadað", "Elmadað");
		data.put("Ankara", map);

		map = new HashMap<String, String>();
		map.put("Þahinbey", "Þahinbey");
		map.put("Þehitkamil", "Þehitkamil");
		data.put("Gaziantep", map);

		map = new HashMap<String, String>();
		map.put("Beyoðlu", "Beyoðlu");
		map.put("Fatih", "Fatih");
		map.put("Zeytinburnu", "Zeytinburnu");
		data.put("Ýstanbul", map);
	}

	public Map<String, Map<String, String>> getData() {
		return data;
	}

	public Map<String, String> getCities() {
		return cities;
	}

	public Map<String, String> getDistricties() {
		return districties;
	}

	public void onCityChange() {
		if (client2.getAdress().getCity() != null)
			districties = data.get(client2.getAdress().getCity());
		else
			districties = new HashMap<String, String>();
	}

}
