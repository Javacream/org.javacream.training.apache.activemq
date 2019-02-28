package org.javacream.training.jms.client_server.selector;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class EchoClient {

	public static void main(String[] args) throws Exception {
		new EchoClient().testJms();
	}
	public void testJms() throws Exception {
		
		String id = "Client" + this.hashCode();
		
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory();
		
		Connection connection = connectionFactory.createConnection();

		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination requestDestination = session.createQueue(EchoConstants.REQUEST_DESTINATION);

		Destination responseDestination = session.createQueue(EchoConstants.RESPONSE_DESTINATION);
		Message request = session.createMessage();
		request.setStringProperty(EchoConstants.CLIENT_IDENTIFIER_KEY, id);
		request.setStringProperty(EchoConstants.PARAM_KEY, "Data from client " + id);

		MessageProducer messageProducer = session.createProducer(requestDestination);
		messageProducer.send(request);
		messageProducer.close();
		connection.start();
		MessageConsumer consumer = session.createConsumer(responseDestination, EchoConstants.CLIENT_IDENTIFIER_KEY
				+ "='" + id + "'");
		System.out.println(consumer.receive().getStringProperty(EchoConstants.RESULT_KEY));
		consumer.close();
		connection.close();
	}

}
