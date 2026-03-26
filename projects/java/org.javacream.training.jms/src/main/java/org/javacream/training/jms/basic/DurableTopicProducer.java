package org.javacream.training.jms.basic;
import javax.jms.Session;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;


public class DurableTopicProducer {

	public static void main(String[] args) throws Exception{
		var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl);
		var connection = connectionFactory.createConnection(BrokerConfiguration.username, BrokerConfiguration.password);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		var destination = session.createTopic(BrokerConfiguration.topicName);
		var message = session.createTextMessage("Hello");
		var messageProducer = session.createProducer(destination);
		messageProducer.send(message);
		connection.close();
		connectionFactory.close();
		
	}

}
