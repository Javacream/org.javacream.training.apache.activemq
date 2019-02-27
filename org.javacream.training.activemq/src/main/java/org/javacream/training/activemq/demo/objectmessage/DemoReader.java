package org.javacream.training.activemq.demo.objectmessage;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class DemoReader {
	public static void main(String[] args) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("s:/customer.ser"));) {
			Customer c = (Customer) ois.readObject();
			System.out.println(c.getLastname());
			System.out.println(c.getGivenName());
			System.out.println(c.getAddress().getStreet());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
