package org.javacream.training.activemq.invoice.business;

import java.io.Serializable;

public class Invoice implements Serializable{

	@Override
	public String toString() {
		return "Invoice [customer=" + customer + ", productId=" + productId
				+ ", amount=" + amount + ", totalPrice=" + totalPrice + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result
				+ ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + (int) (productId ^ (productId >>> 32));
		long temp;
		temp = Double.doubleToLongBits(totalPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Invoice other = (Invoice) obj;
		if (amount != other.amount)
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (productId != other.productId)
			return false;
		if (Double.doubleToLongBits(totalPrice) != Double
				.doubleToLongBits(other.totalPrice))
			return false;
		return true;
	}
	public Customer getCustomer() {
		return customer;
	}
	public long getProductId() {
		return productId;
	}
	public int getAmount() {
		return amount;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public Invoice(Customer customer, long productId, int amount,
			double totalPrice) {
		super();
		this.customer = customer;
		this.productId = productId;
		this.amount = amount;
		this.totalPrice = totalPrice;
	}
	private static final long serialVersionUID = 1L;
	private Customer customer;
	private long productId;
	private int amount;
	private double totalPrice;
	
}
