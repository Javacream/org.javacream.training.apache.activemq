package org.javacream.training.jms.client_server.selector;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class MessageCorrelationEchoServer {

	private static Session session;

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory();
		Connection connection = connectionFactory.createConnection();

		session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(MessageCorrelationEchoConstants.REQUEST_DESTINATION);


		connection.start();
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new DemoMessageListener());
		Object sync = new Object();
		synchronized (sync) {
			sync.wait();
		}
	}

	private static class DemoMessageListener implements MessageListener {

		@Override
		public void onMessage(Message receivedMessage) {
			try {
				String data = receivedMessage.getStringProperty(MessageCorrelationEchoConstants.PARAM_KEY);
				System.out.println("Received message="
						+ data + ", correlation=" + receivedMessage.getJMSCorrelationID());
				Destination responseDestination = session.createQueue(MessageCorrelationEchoConstants.RESPONSE_DESTINATION);
				MessageProducer producer = session.createProducer(responseDestination);
				
				Message response = session.createMessage();
				
				response.setStringProperty(MessageCorrelationEchoConstants.RESULT_KEY, "OK, echoing data " + data);
				response.setJMSCorrelationID(receivedMessage.getJMSCorrelationID());
				producer.send(response);
				producer.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
		}

	}
}
