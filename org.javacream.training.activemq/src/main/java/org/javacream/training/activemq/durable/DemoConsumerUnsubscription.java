package org.javacream.training.activemq.durable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.javacream.training.activemq.util.jms.Constants;
import org.javacream.training.activemq.util.jms.JmsUtil;

public class DemoConsumerUnsubscription {

	private static Session session;

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory(Constants.TCP_URL);
		Connection connection = connectionFactory.createConnection();
		connection.setClientID("sawitzki");

		session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		connection.start();
		session.unsubscribe("demo-messages");
		
		connection.close();
	}

}
