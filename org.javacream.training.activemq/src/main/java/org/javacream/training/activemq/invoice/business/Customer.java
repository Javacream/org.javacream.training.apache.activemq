package org.javacream.training.activemq.invoice.business;

import java.io.Serializable;

public class Customer implements Serializable{
	@Override
	public String toString() {
		return "Customer [name=" + name + ", address=" + address + "]";
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public Customer(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
}
