package org.javacream.training.jms.echo.temporary;

import javax.jms.Session;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;

public class EchoClient {
	public static void main(String[] args) throws Exception{
		var requestDestinationName = "requests";
		var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl);
		var connection = connectionFactory.createConnection(BrokerConfiguration.username, BrokerConfiguration.password);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		var requestDestination = session.createQueue(requestDestinationName);
		var responseDestination = session.createTemporaryQueue();
		var textMessage = session.createTextMessage("Hello Chat");
		var producer = session.createProducer(requestDestination);
		textMessage.setJMSReplyTo(responseDestination);
		producer.send(textMessage);
		var consumer = session.createConsumer(responseDestination);
		connection.start();
		consumer.setMessageListener(m -> System.out.println(m));
		Object sync = new Object();
		synchronized(sync) {
			sync.wait();
		}

		
		
	}

}
