package org.javacream.training.activemq.demo.objectmessage;

import java.io.Serializable;

public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;
	private String lastname;
	private String givenName;
	private Address address;
	public Customer(String lastname, String givenName, Address address) {
		super();
		this.lastname = lastname;
		this.givenName = givenName;
		this.address = address;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}
