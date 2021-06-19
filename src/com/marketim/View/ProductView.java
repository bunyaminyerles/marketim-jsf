package com.marketim.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.marketim.jpaFactory.JpaFactory;
import com.marketim.jpaFactory.impl.JpaFactoryImpl2;
import com.marketim.model.Category;
import com.marketim.model.Product;

public class ProductView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7571037774654761058L;
	private JpaFactory jpaFactory = new JpaFactoryImpl2();
	EntityManager entityManager = jpaFactory.getEntityManager();
	EntityTransaction transaction = jpaFactory.getEntityTransaction();

	private Product product = new Product();
	private Long cid;
	public static int i = 0;
	private UploadedFile file;
	byte[] resim;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {

		this.product = product;
	}

	public Long getCid() {
		
		return product.getCategory().getId();
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void add() {
		transaction.begin();
		product.setCategory(entityManager.find(Category.class, cid));
		entityManager.persist(getProduct());
		transaction.commit();

		product = new Product();

	}

	public void remove() {
		try {

			transaction.begin();

			entityManager.remove(getProduct());

			transaction.commit();

			product = new Product();
			FacesMessage message = new FacesMessage("Ürün silindi");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {

			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

	public void update(Product product) {
		transaction.begin();

		entityManager.merge(product);

		transaction.commit();

	}

	public void update() {
		transaction.begin();
		product.setCategory(entityManager.find(Category.class, cid));
		entityManager.merge(getProduct());

		transaction.commit();

		product = new Product();
	}

	public Product find(Long id) {

		Product tmpProduct = entityManager.find(Product.class, id);
		if (tmpProduct != null) {
			return tmpProduct;
		}
		return null;
	}

	public List<Product> listSearch(String search) {
		try {
			@SuppressWarnings("unchecked")
			List<Product> products = entityManager.createQuery("from Product WHERE productName LIKE '%" + search + "%'")
					.getResultList();
			if (products != null) {

				return products;
			} else {
				return null;
			}

		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hatalý", "Ürünler bulunamadý");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.toString());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		}
	}

	public List<Product> list(Long id) {

		try {
			@SuppressWarnings("unchecked")
			List<Product> products = entityManager.createQuery("from Product WHERE category_Id=" + id).getResultList();

			if (products != null) {
				return products;
			} else {
				return null;
			}
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hatalý", "Ürünler bulunamadý");
				FacesContext.getCurrentInstance().addMessage(null, message);

				return null;
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.toString());
				FacesContext.getCurrentInstance().addMessage(null, message);

				return null;
			}
		}
	}

	public List<Product> list() {

		try {
			@SuppressWarnings("unchecked")
			List<Product> products = entityManager.createQuery("from Product").getResultList();

			if (products != null) {
				return products;
			} else {
				return null;
			}
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hatalý", "Ürünler bulunamadý");
				FacesContext.getCurrentInstance().addMessage(null, message);

				return null;
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.toString());
				FacesContext.getCurrentInstance().addMessage(null, message);

				return null;
			}
		}
	}

	public void openNew() {
		product = new Product();
		product.setProductName(" ");
	}

	public void handleFileUpload(FileUploadEvent event) {
		file = event.getFile();
		try {
			resim = new byte[(int) file.getSize()];
			InputStream ýnputStream = file.getInputStream();
			ýnputStream.read(resim);
			ýnputStream.close();
			product.setPhoto(resim);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FacesMessage message = new FacesMessage("Resim yüklendi");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void save() {
		if (product.getId() == null) {
			add();
			FacesMessage message = new FacesMessage("Ürün eklendi");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			update();
			FacesMessage message = new FacesMessage("Ürün güncellendi");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public List<Category> clist() {

		try {
			@SuppressWarnings("unchecked")
			List<Category> categories = entityManager.createQuery("from Category").getResultList();

			if (categories != null) {
				return categories;
			} else {
				return null;
			}
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query")) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hatalý", "Kategori bulunamadý");
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
