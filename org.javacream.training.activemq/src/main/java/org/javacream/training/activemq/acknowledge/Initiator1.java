package org.javacream.training.activemq.acknowledge;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.javacream.training.activemq.util.jms.JmsBase;
import org.javacream.training.activemq.util.jms.JmsUtil;

public class Initiator1 extends JmsBase {

	public Initiator1() {
		super(false, Session.AUTO_ACKNOWLEDGE);
	}

	public static void main(String[] args) {
		new Initiator1().execute();
	}

	public void execute() {
		Message message = JmsUtil.createMessage(getSession());
		try {
			message.setStringProperty("initiator", "1");
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		Destination destination = JmsUtil.createQueue(getSession(),
				"queue/acknowledge");
		JmsUtil.send(getSession(), destination, message);
		close();
	}
}
