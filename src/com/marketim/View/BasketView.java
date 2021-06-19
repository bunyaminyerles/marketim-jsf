package com.marketim.View;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import com.marketim.jpaFactory.JpaFactory;
import com.marketim.jpaFactory.impl.JpaFactoryImpl;
import com.marketim.model.Basket;
import com.marketim.model.Client;
import com.marketim.model.OrderDetail;
import com.marketim.model.OrderInfo;
import com.marketim.model.Product;

public class BasketView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8682757730920560022L;
	private JpaFactory jpaFactory = new JpaFactoryImpl();
	EntityManager entityManager = jpaFactory.getEntityManager();
	EntityManager entityManager2 = jpaFactory.getEntityManager();
	EntityTransaction transaction = jpaFactory.getEntityTransaction();
	EntityTransaction transaction2 = jpaFactory.getEntityTransaction();
	Double total=(double) 0;
	private Basket basket = new Basket();

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public Basket findBasket(Product product, Client client) {
		try {
			Basket basketTmp = (Basket) entityManager2
					.createQuery("from Basket WHERE product_Id=" + product.getId() + " and clientId=" + client.getId())
					.getSingleResult();

			if (basketTmp != null) {
				return basketTmp;
			} else {
				return null;
			}
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				return null;
			} else {
				Basket basket = new Basket();
				basket.setQuantity("1");
				return basket;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Basket> findBasket(Client client) {
		try {
			List<Basket> baskets = entityManager2.createQuery("from Basket WHERE clientId=" + client.getId())
					.getResultList();

			if (baskets != null) {
				return baskets;
			} else {
				return null;
			}
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Sepette ürün yok");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.toString());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		}
	}

	public void add(Product product, Client client) {

		Basket basketTmp = findBasket(product, client);
		if (basketTmp != null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sepette var",
					"Ürün sepete daha önceden eklenmiþ");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			entityManager.clear();
			transaction.begin();
			basket.setProduct(product);
			basket.setQuantity("1");
			basket.setClient(client);
			client.addProductList(basket);
			entityManager.clear();
			entityManager.persist(getBasket());
			entityManager.flush();
			transaction.commit();
			basket = new Basket();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Baþarýlý", "Ürün sepete eklendi");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

	public void remove(Product product, Client client) {

		transaction.begin();
		entityManager.remove(findBasket(product, client));
		transaction.commit();
	}

	public void quantityINC(Product product, Client client) {
		Basket basketTmp = findBasket(product, client);
		int q = Integer.parseInt(basketTmp.getQuantity());
		int s = Integer.parseInt(basketTmp.getProduct().getStock());
		if (s < 5) {
			if (q < s) {
				q++;
				basketTmp.setQuantity(String.valueOf(q));
				transaction.begin();
				entityManager.merge(basketTmp);
				transaction.commit();
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Stokta yok",
						"Stokta daha fazla ürün yok");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} else {
			if (q < 5) {
				q++;
				basketTmp.setQuantity(String.valueOf(q));
				transaction.begin();
				entityManager.merge(basketTmp);
				transaction.commit();

			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "5 adet sýnýrý",
						"Bir üründen en fazla 5 adet alabilirsiniz");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}

		}
	}

	public void quantityDEC(Product product, Client client) {
		Basket basketTmp = findBasket(product, client);
		int q = Integer.parseInt(basketTmp.getQuantity());
		if (q > 1) {
			q--;
			basketTmp.setQuantity(String.valueOf(q));
			transaction.begin();
			entityManager.merge(basketTmp);
			transaction.commit();
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "1 adet sýnýrý",
					"En az 1 adet ürün olmalýdýr");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Basket> list(Client client) {
		
		try {
			
			List<Basket> baskets = entityManager.createQuery("from Basket WHERE clientId=" + client.getId()).getResultList();
			if (!baskets.isEmpty()) {
				return baskets;
			}
			else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Sepette ürün yok");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		} catch (Exception e) {
		
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.toString());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
		}
	}

	public void toOrder(Client client) {
		try {
			List<Basket> basketTmp = list(client);
			int a = 0;
			if (!basketTmp.get(0).getProduct().getProductName().equals(null)) {
				for (Basket basket : basketTmp) {
					if (Integer.parseInt(basket.getQuantity()) > Integer.parseInt(basket.getProduct().getStock())) {
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								basket.getProduct().getProductName(),
								"Bu üründen sadece " + basket.getProduct().getStock() + " adet kaldý.");
						FacesContext.getCurrentInstance().addMessage(null, message);
						System.out.println(basket.getProduct().getProductName());
						a++;
					}
				}
				if (a == 0) {

					OrderInfo order = new OrderInfo();
					Product product = new Product();
					ProductView productView = new ProductView();
					OrderDetailView orDetailView = new OrderDetailView();
					order.setClient(client);
					LocalDateTime date = LocalDateTime.now();
					Date date2 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
					order.setOrderDate(date2);
					order.setOrderState("Kurye Bekleniyor");
					order.setCourier(null);

					OrderDetail orderDetail;
					for (int i = 0; i < basketTmp.size(); i++) {
						orderDetail = new OrderDetail(basketTmp.get(i).getProduct(), basketTmp.get(i).getQuantity(), order);
						order.addProductList(orderDetail);
						total += Double.parseDouble(basketTmp.get(i).getProduct().getSalePrice())*Double.parseDouble(basketTmp.get(i).getQuantity());
						product = basketTmp.get(i).getProduct();
						remove(basketTmp.get(i).getProduct(), client);
					}
					
					order.setTotalPayment(String.valueOf(total));
					OrderInfoView orderInfoView = new OrderInfoView();
					orderInfoView.setOrder(order);
					orderInfoView.add();
					orDetailView.add(order.getProduct());
					total=(double) 0;
					
					for (int i = 0; i < basketTmp.size(); i++) {
						product = basketTmp.get(i).getProduct();
						product.setStock(String.valueOf(Integer.parseInt(product.getStock())-Integer.parseInt(basketTmp.get(i).getQuantity())));
						productView.update(product);
					}
					
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sipariþiniz bize ulaþtý.", "En kýsa sürede sipariþinizi getireceðiz.");
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		

	}

}
