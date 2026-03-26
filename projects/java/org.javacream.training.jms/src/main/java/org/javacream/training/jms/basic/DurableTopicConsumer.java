package org.javacream.training.jms.basic;

import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;

public class DurableTopicConsumer {

	public static void main(String[] args) throws Exception {
		var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl);
		var connection = connectionFactory.createConnection(BrokerConfiguration.username, BrokerConfiguration.password);
		connection.setClientID("javacream_service");
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		var destination = session.createTopic(BrokerConfiguration.topicName);
		var consumer = session.createDurableConsumer(destination, "instance_2");
		connection.start();
		var message = consumer.receive();
		var textMessage = (TextMessage) message;
		System.out.println(textMessage.getText());
		connection.close();
		connectionFactory.close();
	}

}
