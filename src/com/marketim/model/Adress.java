package com.marketim.model;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class Adress implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5397896350122108477L;
	private String city;
	private String district;
	private String neighborhood;
	private String street;
	private String buildingNumber;
	private String floorNumber;
	private String apartmentNumber;

	public Adress() {
	}

	public Adress(String city, String district, String neighborhood, String street, String buildingNumber,
			String floorNumber, String apartmentNumber) {

		this.city = city;
		this.district = district;
		this.neighborhood = neighborhood;
		this.street = street;
		this.buildingNumber = buildingNumber;
		this.floorNumber = floorNumber;
		this.apartmentNumber = apartmentNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(String apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

}
