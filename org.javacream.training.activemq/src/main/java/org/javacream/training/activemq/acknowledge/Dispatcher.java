package org.javacream.training.activemq.acknowledge;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.activemq.util.jms.JmsBase;
import org.javacream.training.activemq.util.jms.JmsUtil;

public class Dispatcher extends JmsBase {
	private Message initiator1Message;
	private Message initiator2Message;

	public Dispatcher(){
		super(false, Session.CLIENT_ACKNOWLEDGE);
		JmsUtil.setListener(getSession(), JmsUtil.createQueue(getSession(), "queue/acknowledge"), new DispatchingMessageListener());
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
				String initiator;
				initiator = message.getStringProperty("initiator");
				if ("1".equals(initiator)) {
					initiator1Message = message;
					System.out.println("received message from Initiator1");
				}
				if ("2".equals(initiator)) {
					initiator2Message = message;
					System.out.println("received message from Initiator2");
				}
				if (initiator1Message != null && initiator2Message != null) {
					message.acknowledge();
					JmsUtil.send(
							getSession(),
							JmsUtil.createQueue(getSession(), "queue/business"),
							message);
					initiator1Message = null;
					initiator2Message = null;
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
