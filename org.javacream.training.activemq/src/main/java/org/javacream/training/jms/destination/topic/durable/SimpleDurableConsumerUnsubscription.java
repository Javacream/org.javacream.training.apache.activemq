package org.javacream.training.jms.destination.topic.durable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class SimpleDurableConsumerUnsubscription {

	private static Session session;

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory();
		Connection connection = connectionFactory.createConnection();
		connection.setClientID("javacream");

		session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		connection.start();
		session.unsubscribe("durableSubscriptionDemo");
		
		connection.close();
	}

}
