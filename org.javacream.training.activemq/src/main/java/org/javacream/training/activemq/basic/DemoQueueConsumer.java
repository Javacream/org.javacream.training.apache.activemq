package org.javacream.training.activemq.basic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class DemoQueueConsumer {

	public static void main(String[] args) throws JMSException {

		// Wo l�uft das Messaging System
		String url;

		// Verbindung zum Messaging System:
		ConnectionFactory connectionFactory;
		Connection connection;
		Session session;
		Destination destination;

		// Aktives Empfangen einer Message:
		Message message;
		MessageConsumer messageConsumer;

		// Sequenz: Sende Message:

		url = "tcp://localhost:61616";
		connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("queue/Demo");
		messageConsumer = session.createConsumer(destination);
		message = messageConsumer.receive();
		System.out.println(message.getStringProperty("payload"));
		messageConsumer.close();
		session.close();
		connection.close();

	}
}