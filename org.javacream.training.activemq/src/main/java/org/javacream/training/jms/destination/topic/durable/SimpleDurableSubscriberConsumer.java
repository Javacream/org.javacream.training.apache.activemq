package org.javacream.training.jms.destination.topic.durable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.javacream.training.util.jms.JmsUtil;

public class SimpleDurableSubscriberConsumer {

	private static Session session;

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory();
		Connection connection = connectionFactory.createConnection();

		//
		connection.setClientID("javacream");
		//
		session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Topic destination = session.createTopic("JAVACREAM.TOPIC.DURABLE");


		connection.start();
		MessageConsumer consumer = session.createDurableSubscriber(destination, "durableSubscriptionDemo");
		consumer.setMessageListener(new DemoMessageListener());
		// consumer.close();
		// connection.close();

		Object sync = new Object();
		synchronized (sync) {
			sync.wait();
		}
	}

	private static class DemoMessageListener implements MessageListener {

		@Override
		public void onMessage(Message receivedMessage) {
			try {
				System.out.println("###Received message: "
						+ receivedMessage.getStringProperty("toEcho"));
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

	}
}
