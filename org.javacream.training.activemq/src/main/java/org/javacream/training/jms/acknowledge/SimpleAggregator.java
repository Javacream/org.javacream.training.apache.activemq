package org.javacream.training.jms.acknowledge;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsBase;
import org.javacream.training.util.jms.JmsUtil;

public class SimpleAggregator extends JmsBase {
	private Message initiator1Message;
	private Message initiator2Message;

	public SimpleAggregator(){
		super(false, Session.CLIENT_ACKNOWLEDGE);
		JmsUtil.setListener(getSession(), JmsUtil.createQueue(getSession(), AcknowledgeConstants.DESTINATION_AGGREGATOR), new DispatchingMessageListener());
		try {
			getConnection().start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		new SimpleAggregator();
	}
	
	private class DispatchingMessageListener implements MessageListener {

		@Override
		public void onMessage(Message message) {
			try {
				String initiator;
				initiator = message.getStringProperty(AcknowledgeConstants.KEY);
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
							JmsUtil.createQueue(getSession(), AcknowledgeConstants.DESTINATION_CONSUMER),
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
