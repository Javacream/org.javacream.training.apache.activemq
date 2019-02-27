package org.javacream.training.activemq.correlation;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

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
		destination = session.createQueue(CorrelationConstants.DESTINATION);
		messageProducer = session.createProducer(destination);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				message = session.createMessage();
				message.setStringProperty("payload", "Hello JMS!");
				message.setJMSCorrelationID(Integer.toString(i));
				messageProducer.send(message);
			}
		}
		messageProducer.close();
		session.close();
		connection.close();

	}
}
