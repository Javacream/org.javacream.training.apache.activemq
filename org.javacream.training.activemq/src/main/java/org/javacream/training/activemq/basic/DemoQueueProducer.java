package org.javacream.training.activemq.basic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class DemoQueueProducer {

	public static void main(String[] args) throws JMSException {

		// Wo l�uft das Messaging System
		String url;

		// Verbindung zum Messaging System:
		ConnectionFactory connectionFactory;
		Connection connection;
		Session session;
		Destination destination;

		// Aktives Versenden einer Message:
		Message message;
		MessageProducer messageProducer;

		// Sequenz: Sende Message:

		url = "tcp://localhost:61616";
		connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("queue/Demo");
		message = session.createMessage();
		message.setStringProperty("payload", "Hello JMS!");
		messageProducer = session.createProducer(destination);

		messageProducer.send(message);

		messageProducer.close();
		session.close();
		connection.close();

	}
}
