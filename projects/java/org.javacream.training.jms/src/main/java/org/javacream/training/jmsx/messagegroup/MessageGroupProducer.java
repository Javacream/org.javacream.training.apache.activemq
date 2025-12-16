package org.javacream.training.jmsx.messagegroup;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.jms.ApplicationConfiguration;
import org.javacream.training.jms.util.JmsUtil;

public class MessageGroupProducer {

	public static void main(String[] args) throws JMSException {

		ConnectionFactory connectionFactory;
		Connection connection;
		Session session;
		Destination destination;

		Message message;
		MessageProducer messageProducer;


		connectionFactory = JmsUtil.getConnectionFactory();
		connection = connectionFactory.createConnection(ApplicationConfiguration.username, ApplicationConfiguration.password);
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue(MessageGroupConstants.DESTINATION);
		messageProducer = session.createProducer(destination);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				message = session.createMessage();
				message.setStringProperty(MessageGroupConstants.PARAM_KEY, "Hello JMS!" + i);
				message.setStringProperty(MessageGroupConstants.MESSAGE_GROUP_KEY, "Javacream");

				messageProducer.send(message);
			}
		}
		messageProducer.close();
		session.close();
		connection.close();

	}
}
