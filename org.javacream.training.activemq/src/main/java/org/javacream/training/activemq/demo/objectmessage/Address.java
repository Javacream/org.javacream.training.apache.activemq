package org.javacream.training.activemq.demo.objectmessage;

import java.io.Serializable;

public class Address implements Serializable{
	private static final long serialVersionUID = 1L;
	private String city;
	public Address(String city, String street) {
		super();
		this.city = city;
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	private String street;
}
