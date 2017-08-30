package org.javacream.training.activemq.demo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.activemq.util.jms.Constants;
import org.javacream.training.activemq.util.jms.JmsUtil;

public class DemoProducer {

	public static void main(String[] args) throws Exception {
		new DemoProducer().testJms();
	}
	public void testJms() throws Exception {
		
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory(Constants.TCP_URL);
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic("DEMO_TOPIC");
		Message request = session.createMessage();
		request.setStringProperty("message", "Hello!");
		
		MessageProducer messageProducer = session.createProducer(destination);
		messageProducer.send(request);
		messageProducer.close();
		connection.close();
	}

}
