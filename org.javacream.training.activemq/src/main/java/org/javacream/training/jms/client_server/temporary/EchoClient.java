package org.javacream.training.jms.client_server.temporary;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;

import org.javacream.training.util.jms.JmsUtil;

public class EchoClient {

	public static void main(String[] args) throws Exception {
		new EchoClient().testJms();
	}
	public void testJms() throws Exception {
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory();
		
		Connection connection = connectionFactory.createConnection();

		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(EchoConstants.REQUEST_DESTINATION);


		TemporaryQueue temporaryQueue = session.createTemporaryQueue();
		
		Message request = session.createMessage();
		request.setJMSReplyTo(temporaryQueue);
		request.setStringProperty(EchoConstants.PARAM_KEY, "Data from client " + this);

		MessageProducer messageProducer = session.createProducer(destination);
		messageProducer.send(request);
		messageProducer.close();
		connection.start();
		MessageConsumer consumer = session.createConsumer(temporaryQueue);
		System.out.println(consumer.receive().getStringProperty(EchoConstants.RESULT_KEY));
		consumer.close();
		connection.close();
	}

}
