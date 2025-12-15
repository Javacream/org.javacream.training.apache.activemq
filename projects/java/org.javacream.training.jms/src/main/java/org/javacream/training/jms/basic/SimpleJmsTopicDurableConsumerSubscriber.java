package org.javacream.training.jms.basic;

import static org.javacream.training.jms.ApplicationConfiguration.brokerURL;
import static org.javacream.training.jms.ApplicationConfiguration.clientId;
import static org.javacream.training.jms.ApplicationConfiguration.password;
import static org.javacream.training.jms.ApplicationConfiguration.subscription;
import static org.javacream.training.jms.ApplicationConfiguration.topicName;
import static org.javacream.training.jms.ApplicationConfiguration.username;

import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class SimpleJmsTopicDurableConsumerSubscriber {
	
	public Session createSession() throws Exception{
		var connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		var connection = connectionFactory.createConnection(username, password);
		connection.setClientID(clientId);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		connection.start();
		return session;
	}
	
	public Topic getTopic(Session session) throws Exception {
		return session.createTopic(topicName);
	}
	
	public void createSubscription(Session session, String name) throws Exception {
		session.createDurableConsumer(getTopic(session), name);
	}
	
	public static void main(String[] args) throws Exception{
		var simpleJmsProducer = new SimpleJmsTopicDurableConsumerSubscriber();
		var session = simpleJmsProducer.createSession();
		simpleJmsProducer.createSubscription(session, subscription);
	}
	

}
