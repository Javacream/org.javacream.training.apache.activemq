package org.javacream.training.activemq.client_server.selector;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.activemq.util.jms.JmsUtil;

public class EchoServer {

	private static Session session;

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();

		session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(EchoConstants.DESTINATION);


		connection.start();
		MessageConsumer consumer = session.createConsumer(destination);
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
				String payload = receivedMessage.getStringProperty(EchoConstants.PAYLOAD);
				System.out.println("Received message: "
						+ payload);
				MessageProducer producer = session.createProducer(receivedMessage.getJMSReplyTo());
				
				Message response = session.createMessage();
				
				response.setStringProperty(EchoConstants.RESULT, "OK, echoing payload " + payload);
				response.setStringProperty(EchoConstants.CLIENT_IDENTIFIER_KEY, receivedMessage.getStringProperty(EchoConstants.CLIENT_IDENTIFIER_KEY));
				producer.send(response);
				producer.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
		}

	}
}
