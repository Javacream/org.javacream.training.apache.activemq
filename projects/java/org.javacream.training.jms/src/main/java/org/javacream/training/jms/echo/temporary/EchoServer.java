package org.javacream.training.jms.echo.temporary;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;

public class EchoServer {
	public static void main(String[] args) throws Exception {
		var requestDestinationName = "requests";

		var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl);
		var connection = connectionFactory.createConnection(BrokerConfiguration.username, BrokerConfiguration.password);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		var requestDestination = session.createQueue(requestDestinationName);

		connection.start();
		var consumer = session.createConsumer(requestDestination);

		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try {
					var responseDestination = message.getJMSReplyTo();
					var producer = session.createProducer(responseDestination);
					System.out.println("Received message " + message);
					var responseMessage = session.createTextMessage(((TextMessage)message).getText());
					producer.send(responseMessage);
					producer.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		Object sync = new Object();
		synchronized(sync) {
			sync.wait();
		}
	}
}
