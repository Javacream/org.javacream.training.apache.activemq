package org.javacream.training.jms.destination.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class SimpleTopicProducer {

	public static void main(String[] args) throws Exception {
		new SimpleTopicProducer().testJms();
	}
	public void testJms() throws Exception {
		
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory();
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic("topic/A");
		Message request = session.createMessage();
		request.setStringProperty("message", "Hello!");
		
		MessageProducer messageProducer = session.createProducer(destination);
		messageProducer.send(request);
		messageProducer.close();
		connection.close();
	}

}
