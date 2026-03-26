package org.javacream.training.jms.basic;
import javax.jms.Session;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;

public class DurableTopicUnsubscriber {

	public static void main(String[] args) throws Exception{
		var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl);
		var connection = connectionFactory.createConnection(BrokerConfiguration.username, BrokerConfiguration.password);
		connection.setClientID("javacream_service");
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		session.unsubscribe("instance_1");
		connection.close();
		connectionFactory.close();
	}

}
