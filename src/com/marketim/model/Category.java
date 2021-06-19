package com.marketim.model;

import javax.persistence.Version;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7397799544405223883L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String categoryName;
	@Lob
	private byte[] categoryPhoto;
	@Version
	private Long version;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public byte[] getCategoryPhoto() {
		return categoryPhoto;
	}

	public void setCategoryPhoto(byte[] categoryPhoto) {
		this.categoryPhoto = categoryPhoto;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	

}
