package org.javacream.training.activemq.advisory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.activemq.util.jms.Constants;
import org.javacream.training.activemq.util.jms.JmsUtil;

public class AdvisoryConsumer {

	private static Session session;

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory(Constants.TCP_URL);
		Connection connection = connectionFactory.createConnection();

		session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic("ActiveMQ.Advisory.Queue");


		connection.start();
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new AdvisoryMessageListener());
		// consumer.close();
		// connection.close();

		Object sync = new Object();
		synchronized (sync) {
			sync.wait();
		}
	}

	private static class AdvisoryMessageListener implements MessageListener {

		@Override
		public void onMessage(Message receivedMessage) {
			try {
				System.out.println("Received message: "
						+ receivedMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
