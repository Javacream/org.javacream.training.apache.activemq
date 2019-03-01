package org.javacream.training.jms.acknowledge;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsBase;
import org.javacream.training.util.jms.JmsUtil;

public class Message2Producer extends JmsBase{

	public Message2Producer(){
		super(false, Session.AUTO_ACKNOWLEDGE);
	}

	public static void main(String[] args) {
		new Message2Producer().execute();
	}
	
	public void execute(){
		Message message = JmsUtil.createMessage(getSession());
		try {
			message.setStringProperty(AcknowledgeConstants.KEY, "2");
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		Destination destination = JmsUtil.createQueue(getSession(), AcknowledgeConstants.DESTINATION_AGGREGATOR);
		JmsUtil.send(getSession(), destination, message);
		close();
	}
}
