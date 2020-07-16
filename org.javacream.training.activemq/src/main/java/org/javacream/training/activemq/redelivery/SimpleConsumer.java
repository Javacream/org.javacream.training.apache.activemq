package org.javacream.training.activemq.redelivery;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class SimpleConsumer{

	public SimpleConsumer() throws JMSException{
		Connection connection = JmsUtil.getConnectionFactory().createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		JmsUtil.setListener(session, JmsUtil.createQueue(session, TransactionalConstants.DESTINATION_CONSUMER), new SimpleMessageListener());
		try {
			connection.start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}

	}
	public static void main(String[] args) throws JMSException {
		new SimpleConsumer();
	}
	
	private class SimpleMessageListener implements MessageListener{

		@Override
		public void onMessage(Message message) {
			System.out.println("received message: " + message);
		}
		
	}
}
