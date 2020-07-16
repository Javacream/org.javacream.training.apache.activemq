package org.javacream.training.jms.acknowledge;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class SimpleAggregator{
	private Message initiator1Message;
	private Message initiator2Message;
	private Session session;

	public SimpleAggregator() throws JMSException {
		Connection connection = JmsUtil.getConnectionFactory().createConnection();
		session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		JmsUtil.setListener(session, JmsUtil.createQueue(session, AcknowledgeConstants.DESTINATION_AGGREGATOR), new DispatchingMessageListener());
		try {
			connection.start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws JMSException {
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
							session,
							JmsUtil.createQueue(session, AcknowledgeConstants.DESTINATION_CONSUMER),
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
