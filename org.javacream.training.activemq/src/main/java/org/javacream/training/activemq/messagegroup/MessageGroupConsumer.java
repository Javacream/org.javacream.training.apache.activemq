package org.javacream.training.activemq.messagegroup;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class MessageGroupConsumer {

	private static Session session;

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory();
		Connection connection = connectionFactory.createConnection();

		session = connection.createSession(false,
				Session.CLIENT_ACKNOWLEDGE);
		Destination destination = session.createQueue(MessageGroupConstants.DESTINATION);


		connection.start();
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new CorrelationMessageListener());
		// consumer.close();
		// connection.close();

		Object sync = new Object();
		synchronized (sync) {
			sync.wait();
		}
	}

	private static class CorrelationMessageListener implements MessageListener {

		private int counter;

		@Override
		public void onMessage(Message receivedMessage) {
			try {
				System.out.print("Received message by " + this + ", message="
						+ receivedMessage.getStringProperty(MessageGroupConstants.PARAM_KEY) + ", messageGroupIdId=" + receivedMessage.getStringProperty(MessageGroupConstants.MESSAGE_GROUP_KEY));
				counter ++;
				if (counter == 3){
					System.out.println(", acknowleded");
					counter =0;
					receivedMessage.acknowledge();
				}
				else{
					System.out.println();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
		}

	}
}
