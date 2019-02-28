package org.javacream.training.jms.destination.topic.durable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class DemoProducer {

	public static void main(String[] args) throws Exception {
		new DemoProducer().testJms();
	}
	public void testJms() throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory();
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic("topic/Durable");
		Message request = session.createMessage();
		request.setStringProperty("toEcho", "goodbye!");
		MessageProducer messageProducer = session.createProducer(destination);
		messageProducer.send(request);
		messageProducer.close();
		connection.close();
	}

}
