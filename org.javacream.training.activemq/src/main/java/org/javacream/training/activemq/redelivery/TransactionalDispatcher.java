package org.javacream.training.activemq.redelivery;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class TransactionalDispatcher{
	private int rollbackCounter = 0;
	private static final int MAX_ROLLBACKS = 10;
	private Session session;

	public TransactionalDispatcher() throws JMSException{
		Connection connection = JmsUtil.getConnectionFactory().createConnection();
		session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);

		JmsUtil.setListener(session, JmsUtil.createQueue(session, TransactionalConstants.DESTINATION_AGGREGATOR), new DispatchingMessageListener());
		try {
			connection.start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws JMSException {
		new TransactionalDispatcher();
	}
	
	private class DispatchingMessageListener implements MessageListener {

		@Override
		public void onMessage(Message message) {
			try {
			System.out.println("received message " + message);
			JmsUtil.send(session, JmsUtil.createQueue(session, TransactionalConstants.DESTINATION_CONSUMER), message);
			if (rollbackCounter < MAX_ROLLBACKS){
				System.out.println("rollback for original message");
				session.rollback();
				rollbackCounter++;
			}else{
				System.out.println("committing the redelivered message");
				session.commit();
				rollbackCounter = 0;
				
			}
			} catch (JMSException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
