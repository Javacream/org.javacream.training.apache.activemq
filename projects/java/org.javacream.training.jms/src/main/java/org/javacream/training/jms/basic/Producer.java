package org.javacream.training.jms.basic;
import javax.jms.Session;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;


public class Producer {

	public static void main(String[] args) throws Exception{
		var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl);
		var connection = connectionFactory.createConnection(BrokerConfiguration.username, BrokerConfiguration.password);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		var destination = session.createQueue(BrokerConfiguration.queueName);
		//var destination = session.createTopic(BrokerConfiguration.topicName);
		var message = session.createTextMessage("Hello");
		message.setStringProperty("hugo", "Emil");
		var messageProducer = session.createProducer(destination);
		messageProducer.send(message);
		connection.close();
		connectionFactory.close();
		
	}

}
