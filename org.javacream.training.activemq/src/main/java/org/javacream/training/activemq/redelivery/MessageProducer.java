package org.javacream.training.activemq.redelivery;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class MessageProducer{

	public static void main(String[] args) throws JMSException {
		new MessageProducer().execute();
	}

	public void execute() throws JMSException {
		Connection connection = JmsUtil.getConnectionFactory().createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Message message = JmsUtil.createMessage(session);
		Destination destination = JmsUtil.createQueue(session,
				TransactionalConstants.DESTINATION_AGGREGATOR);
		JmsUtil.send(session, destination, message);
		connection.close();
	}
}
