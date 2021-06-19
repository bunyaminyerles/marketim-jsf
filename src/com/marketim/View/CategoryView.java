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

public class CategoryView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4736353715513821428L;
	private JpaFactory jpaFactory = new JpaFactoryImpl2();
	EntityManager entityManager = jpaFactory.getEntityManager();
	EntityTransaction transaction = jpaFactory.getEntityTransaction();

	private Category category = new Category();
	private UploadedFile file;
	byte [] resim;
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void add() {
		transaction.begin();
		
		entityManager.persist(getCategory());

		transaction.commit();

		category = new Category();

	}
	
	public void remove() {
		try {
		
		transaction.begin();

		entityManager.remove(getCategory());

		transaction.commit();

		category = new Category();
		FacesMessage message = new FacesMessage("Kategori silindi");
		FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		

	}
	
	public void update() {
		transaction.begin();

		entityManager.merge(getCategory());

		transaction.commit();

		category = new Category();
	}
	
	public void openNew() {
		category = new Category();
		category.setCategoryName(" ");
	}
	
	public String categorySelection(Long id) {
		category = find(id);
		return "product.xhtml?faces-redirect=true";
	}

	public Category find(Long id) {
		Category tmpCategory = entityManager.find(Category.class, id);
		if (tmpCategory != null) {
			return tmpCategory;
		}
		return null;
	}

	public List<Category> list() {

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
	
	public void handleFileUpload(FileUploadEvent event) {
		file = event.getFile();
		try {
			resim = new byte[(int)file.getSize()];
			InputStream ýnputStream =  file.getInputStream();
			ýnputStream.read(resim);
			ýnputStream.close();
			category.setCategoryPhoto(resim);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FacesMessage message = new FacesMessage("Resim yüklendi");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void save() {
		if (category.getId()==null) {
			add();
			FacesMessage message = new FacesMessage("Kategori eklendi");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		else {
			update();
			FacesMessage message = new FacesMessage("Kategori güncellendi");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

}
