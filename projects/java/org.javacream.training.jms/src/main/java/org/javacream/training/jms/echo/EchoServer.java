package org.javacream.training.jms.echo;

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
		var responseDestinationName = "responses";
		var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl);
		var connection = connectionFactory.createConnection(BrokerConfiguration.username, BrokerConfiguration.password);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		var requestDestination = session.createQueue(requestDestinationName);
		var responseDestination = session.createQueue(responseDestinationName);
		connection.start();
		var consumer = session.createConsumer(requestDestination);
		var producer = session.createProducer(responseDestination);

		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				System.out.println("Received message " + message);
				try {
					var responseMessage = session.createTextMessage(((TextMessage)message).getText());
					responseMessage.setJMSCorrelationID(message.getJMSMessageID());
					producer.send(responseMessage);
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
