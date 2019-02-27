package org.javacream.training.activemq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.activemq.util.jms.JmsUtil;

public class DemoProducer {

	public static void main(String[] args) throws Exception {
		new DemoProducer().testJms();
	}

	public void testJms() throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory();
		Connection connection = connectionFactory.createConnection("system", "system");
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("queue/A");
		Message request = session.createMessage();
		request.setStringProperty("payload", "Hello!");
		MessageProducer messageProducer = session.createProducer(destination);

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1042; i++) {
			messageProducer.send(request);
		}
		System.out.println("Sending 1042 messages took "
				+ (System.currentTimeMillis() - start));
		messageProducer.close();
		connection.close();
	}

}
