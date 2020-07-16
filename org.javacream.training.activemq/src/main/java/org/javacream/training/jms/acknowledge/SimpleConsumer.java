package org.javacream.training.jms.acknowledge;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class SimpleConsumer{

	private Session session;

	public SimpleConsumer() throws JMSException{
		Connection connection = JmsUtil.getConnectionFactory().createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		JmsUtil.setListener(session, JmsUtil.createQueue(session, AcknowledgeConstants.DESTINATION_CONSUMER), new BusinessMessageListener());
		try {
			connection.start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}

	}
	public static void main(String[] args) throws JMSException {
		new SimpleConsumer();
	}
	
	private class BusinessMessageListener implements MessageListener{

		@Override
		public void onMessage(Message message) {
			System.out.println("received message: " + message);
		}
		
	}
}
