package org.javacream.training.jms.acknowledge;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class Message1Producer{

	public static void main(String[] args) throws JMSException{
		new Message1Producer().execute();
	}

	public void execute() throws JMSException{
		Session session = JmsUtil.getConnectionFactory().createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
		Message message = JmsUtil.createMessage(session);
		try {
			message.setStringProperty(AcknowledgeConstants.KEY, "1");
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		Destination destination = JmsUtil.createQueue(session,
				AcknowledgeConstants.DESTINATION_AGGREGATOR);
		JmsUtil.send(session, destination, message);
	}
}
