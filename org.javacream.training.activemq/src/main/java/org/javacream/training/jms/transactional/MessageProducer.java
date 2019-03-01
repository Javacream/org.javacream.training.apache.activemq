package org.javacream.training.jms.transactional;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsBase;
import org.javacream.training.util.jms.JmsUtil;

public class MessageProducer extends JmsBase {

	public MessageProducer() {
		super(false, Session.AUTO_ACKNOWLEDGE);
	}

	public static void main(String[] args) {
		new MessageProducer().execute();
	}

	public void execute() {
		Message message = JmsUtil.createMessage(getSession());
		Destination destination = JmsUtil.createQueue(getSession(),
				TransactionalConstants.DESTINATION_AGGREGATOR);
		JmsUtil.send(getSession(), destination, message);
		close();
	}
}
