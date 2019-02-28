package org.javacream.training.activemq.messagegroup;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class Producer {

	public static void main(String[] args) throws JMSException {

		ConnectionFactory connectionFactory;
		Connection connection;
		Session session;
		Destination destination;

		Message message;
		MessageProducer messageProducer;


		connectionFactory = JmsUtil.getConnectionFactory();
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue(Constants.DESTINATION);
		messageProducer = session.createProducer(destination);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				message = session.createMessage();
				message.setStringProperty(Constants.PARAM_KEY, "Hello JMS!");
				message.setStringProperty(Constants.MESSAGE_GROUP_KEY, "Number" + i);

				messageProducer.send(message);
			}
		}
		messageProducer.close();
		session.close();
		connection.close();

	}
}
