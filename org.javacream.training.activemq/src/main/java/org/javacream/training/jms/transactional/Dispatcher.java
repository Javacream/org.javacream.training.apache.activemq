package org.javacream.training.jms.transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsBase;
import org.javacream.training.util.jms.JmsUtil;

public class Dispatcher extends JmsBase {

	public Dispatcher(){
		super(true, Session.CLIENT_ACKNOWLEDGE);
		JmsUtil.setListener(getSession(), JmsUtil.createQueue(getSession(), "queue/transactional"), new DispatchingMessageListener());
		try {
			getConnection().start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		new Dispatcher();
	}
	
	private class DispatchingMessageListener implements MessageListener {

		@Override
		public void onMessage(Message message) {
			try {
			System.out.println("received message " + message);
			if (message.getJMSRedelivered()){
				JmsUtil.send(getSession(), JmsUtil.createQueue(getSession(), "queue/transactionalBusiness"), message);
				System.out.println("commit");
				getSession().commit();
			}else{
				JmsUtil.send(getSession(), JmsUtil.createQueue(getSession(), "queue/transactionalBusiness"), message);
				System.out.println("rollback");
				getSession().rollback();
				
			}
			} catch (JMSException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
