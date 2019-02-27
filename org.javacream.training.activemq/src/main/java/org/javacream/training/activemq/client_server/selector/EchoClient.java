package org.javacream.training.activemq.client_server.selector;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.activemq.util.jms.JmsUtil;

public class EchoClient {

	public static void main(String[] args) throws Exception {
		new EchoClient().testJms();
	}
	public void testJms() throws Exception {
		
		String id = "Client123";
		
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory("tcp://localhost:61616");
		
		Connection connection = connectionFactory.createConnection();

		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(EchoConstants.DESTINATION);


//		TemporaryQueue temporaryQueue = session.createTemporaryQueue();
		Destination replyTo = session.createQueue(EchoConstants.REPLY_TO_DESTINATION);
		Message request = session.createMessage();
		request.setJMSReplyTo(replyTo);
		request.setStringProperty(EchoConstants.CLIENT_IDENTIFIER_KEY, id);
		request.setStringProperty(EchoConstants.PAYLOAD, "Sawitzki!");

		MessageProducer messageProducer = session.createProducer(destination);
		messageProducer.send(request);
		messageProducer.close();
		connection.start();
		MessageConsumer consumer = session.createConsumer(replyTo, EchoConstants.CLIENT_IDENTIFIER_KEY
				+ "=" + id);
		System.out.println(consumer.receive().getStringProperty(EchoConstants.RESULT));
		consumer.close();
		connection.close();
	}

}
