package org.javacream.training.activemq.masterslave;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class SimpleQueueConsumer {

	private static Session session;
	private static final String URL = "failover:(tcp://localhost:61616,tcp://localhost:51515)?initialReconnectDelay=100";
	public static void main(String[] args) throws Exception {
		SimpleQueueConsumer sqc = new SimpleQueueConsumer();
		sqc.registerConsumerBroker(URL);
		Object sync = new Object();
		synchronized (sync) {
			sync.wait();
		}
	}

	private void registerConsumerBroker(String url) throws JMSException {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("JAVACREAM.QUEUE.A");
		connection.start();
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new DemoMessageListener(url));
	}

	private static class DemoMessageListener implements MessageListener {
		private String url;
		
		public DemoMessageListener(String url) {
			this.url = url;
		}
		@Override
		public void onMessage(Message receivedMessage) {
			try {
				System.out.println("Broker@" + url + " received message: " + receivedMessage.getStringProperty("param"));
			} catch (JMSException e) {
				System.err.println("Exception receiving message: " + e.getMessage());
			}
		}

	}
}
