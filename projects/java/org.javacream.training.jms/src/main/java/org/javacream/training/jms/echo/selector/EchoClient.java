package org.javacream.training.jms.echo.selector;

import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;

public class EchoClient {
	public static void main(String[] args) throws Exception{
		var requestDestinationName = "requests";
		var responseDestinationName = "responses";
		var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl);
		var connection = connectionFactory.createConnection(BrokerConfiguration.username, BrokerConfiguration.password);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		var requestDestination = session.createQueue(requestDestinationName);
		var responseDestination = session.createQueue(responseDestinationName);
		var textMessage = session.createTextMessage("Hello");
		var producer = session.createProducer(requestDestination);
		producer.send(textMessage);
		var messageId = textMessage.getJMSMessageID();
		System.out.println(messageId);
		var consumer = session.createConsumer(responseDestination, "JMSCorrelationID = '" + messageId + "'");
		connection.start();
		var response = (TextMessage)consumer.receive();
		System.out.println(response.getText());
		connection.close();
		connectionFactory.close();
		
	}

}
