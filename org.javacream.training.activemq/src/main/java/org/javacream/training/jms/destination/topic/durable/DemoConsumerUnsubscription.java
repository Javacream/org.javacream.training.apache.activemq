package org.javacream.training.jms.destination.topic.durable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class DemoConsumerUnsubscription {

	private static Session session;

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory();
		Connection connection = connectionFactory.createConnection();
		connection.setClientID("sawitzki");

		session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		connection.start();
		session.unsubscribe("demo-messages");
		
		connection.close();
	}

}
