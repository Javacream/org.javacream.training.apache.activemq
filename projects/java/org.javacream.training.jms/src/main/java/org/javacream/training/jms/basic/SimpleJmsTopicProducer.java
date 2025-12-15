package org.javacream.training.jms.basic;

import static org.javacream.training.jms.ApplicationConfiguration.brokerURL;
import static org.javacream.training.jms.ApplicationConfiguration.password;
import static org.javacream.training.jms.ApplicationConfiguration.topicName;
import static org.javacream.training.jms.ApplicationConfiguration.username;

import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class SimpleJmsTopicProducer {
	
	public Session createSession() throws Exception{
		var connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		var connection = connectionFactory.createConnection(username, password);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		return session;
	}
	
	public Topic getTopic(Session session) throws Exception {
		return session.createTopic(topicName);
	}
	
	public void sendMessage(Session session, String text) throws Exception {
		var message = session.createTextMessage(text);
		var messageProducer = session.createProducer(getTopic(session));
		messageProducer.send(message);
	}
	
	public static void main(String[] args) throws Exception{
		var simpleJmsProducer = new SimpleJmsTopicProducer();
		var session = simpleJmsProducer.createSession();
		simpleJmsProducer.sendMessage(session, "Hugo");
	}
	

}
