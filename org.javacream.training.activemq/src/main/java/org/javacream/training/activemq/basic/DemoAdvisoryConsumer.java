package org.javacream.training.activemq.basic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class DemoAdvisoryConsumer {

	public static void main(String[] args) throws JMSException {

		// Wo l�uft das Messaging System
		String url;

		// Verbindung zum Messaging System:
		ConnectionFactory connectionFactory;
		Connection connection;
		Session session;
		Destination destination;

		// Sequenz: Sende Message:

		url = "tcp://localhost:61616";
		connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createTopic("ActiveMQ.Advisory.Connection");
		
		session.createConsumer(destination).setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				System.out.println(message);
			}
		});
//		messageConsumer.close();
//		session.close();
//		connection.close();

	}
	
	
}
