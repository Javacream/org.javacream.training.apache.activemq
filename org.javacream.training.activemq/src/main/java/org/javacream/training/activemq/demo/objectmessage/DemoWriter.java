package org.javacream.training.activemq.demo.objectmessage;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class DemoWriter {

	public static void main(String[] args) throws Exception{
		Customer customer = new Customer("Sawitzki", "Rainer", new Address("Munich", "Marienplatz 8"));
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("s:/customer.ser"));
		oos.writeObject(customer);
		oos.close();
	}

}
