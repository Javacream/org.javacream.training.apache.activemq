package org.javacream.training.activemq.security;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class SimpleQueueProducer {

	public static void main(String[] args) throws Exception {
		new SimpleQueueProducer().testJms();
	}

	public void testJms() throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory();
		Connection connection = connectionFactory.createConnection("user", "password");
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("JAVACREAM.USERS.A");
		Message request = session.createMessage();
		request.setStringProperty("param", "Hello!");
		MessageProducer messageProducer = session.createProducer(destination);
		messageProducer.send(request);
		messageProducer.close();
		connection.close();
	} 

}
