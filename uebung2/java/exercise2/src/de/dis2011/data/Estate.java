package de.dis2011.data;

import java.util.List;

/**
 * CREATE TABLE estate ( id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START
 * WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY, city VARCHAR(255), postalCode
 * VARCHAR(5), street VARCHAR(255), streetNumber INTEGER, squareArea
 * VARCHAR(255), estateAgentId INTEGER, CONSTRAINT fkEstateAgentId FOREIGN
 * KEY(estateAgentId) REFERENCES estateAgent(id) );
 *
 */
public abstract class Estate {
	private int id = -1;
	private String city;
	private String postalCode;
	private String street;
	private int streetNumber;
	private String squareArea;
	private int estateAgentId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getSquareArea() {
		return squareArea;
	}

	public void setSquareArea(String squareArea) {
		this.squareArea = squareArea;
	}

	public int getEstateAgentId() {
		return estateAgentId;
	}

	public void setEstateAgentId(int estateAgentId) {
		this.estateAgentId = estateAgentId;
	}

	public static Estate load(int id) {
		Apartment apartment = Apartment.load(id);
		if (apartment != null) return apartment;
		// TODO house check
		return null;
	}

	public static List<Estate> loadAll() {
		List<Estate> apartments = Apartment.loadAll();
		// TODO houses
		if (apartments != null) return apartments;
		return null;
	}

	public void save() {
	}

	public void delete() {
	}
}
