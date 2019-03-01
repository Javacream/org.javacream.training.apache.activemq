package org.javacream.training.jms.transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsBase;
import org.javacream.training.util.jms.JmsUtil;

public class TransactionalDispatcher extends JmsBase {

	public TransactionalDispatcher(){
		super(true, Session.CLIENT_ACKNOWLEDGE);
		JmsUtil.setListener(getSession(), JmsUtil.createQueue(getSession(), TransactionalConstants.DESTINATION_AGGREGATOR), new DispatchingMessageListener());
		try {
			getConnection().start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		new TransactionalDispatcher();
	}
	
	private class DispatchingMessageListener implements MessageListener {

		@Override
		public void onMessage(Message message) {
			try {
			System.out.println("received message " + message);
			if (message.getJMSRedelivered()){
				JmsUtil.send(getSession(), JmsUtil.createQueue(getSession(), TransactionalConstants.DESTINATION_CONSUMER), message);
				System.out.println("committing the redelivered message");
				getSession().commit();
			}else{
				JmsUtil.send(getSession(), JmsUtil.createQueue(getSession(), TransactionalConstants.DESTINATION_CONSUMER), message);
				System.out.println("rollback for original message");
				getSession().rollback();
				
			}
			} catch (JMSException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
