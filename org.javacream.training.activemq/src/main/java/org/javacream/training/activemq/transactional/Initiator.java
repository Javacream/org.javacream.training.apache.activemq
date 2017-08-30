package org.javacream.training.activemq.transactional;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Session;

import org.javacream.training.activemq.util.jms.JmsBase;
import org.javacream.training.activemq.util.jms.JmsUtil;

public class Initiator extends JmsBase {

	public Initiator() {
		super(false, Session.AUTO_ACKNOWLEDGE);
	}

	public static void main(String[] args) {
		new Initiator().execute();
	}

	public void execute() {
		Message message = JmsUtil.createMessage(getSession());
		Destination destination = JmsUtil.createQueue(getSession(),
				"queue/transactional");
		JmsUtil.send(getSession(), destination, message);
		close();
	}
}
