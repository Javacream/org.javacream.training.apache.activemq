package org.javacream.training.jms.acknowledge;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsBase;
import org.javacream.training.util.jms.JmsUtil;

public class Business extends JmsBase{

	public Business(){
		super(false, Session.AUTO_ACKNOWLEDGE);
		JmsUtil.setListener(getSession(), JmsUtil.createQueue(getSession(), "JAVACREAM.ACKNOWLEDGE.BUSINESS"), new BusinessMessageListener());
		try {
			getConnection().start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}

	}
	public static void main(String[] args) {
		new Business();
	}
	
	private class BusinessMessageListener implements MessageListener{

		@Override
		public void onMessage(Message message) {
			System.out.println("received message: " + message);
		}
		
	}
}
