package org.javacream.training.jms.basic;

import static org.javacream.training.jms.ApplicationConfiguration.brokerURL;
import static org.javacream.training.jms.ApplicationConfiguration.password;
import static org.javacream.training.jms.ApplicationConfiguration.topicName;
import static org.javacream.training.jms.ApplicationConfiguration.username;

import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class SimpleJmsTopicConsumer {
	
	public Session createSession() throws Exception{
		var connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		var connection = connectionFactory.createConnection(username, password);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		connection.start();
		return session;
	}
	
	public Topic getTopic(Session session) throws Exception {
		return session.createTopic(topicName);
	}
	
	public void receiveMessage(Session session) throws Exception {
		var messageConsumer = session.createConsumer(getTopic(session));
		var message = messageConsumer.receive();
		var textMessage = (TextMessage) message;
		var text = textMessage.getText();
		System.out.println(text);
	}
	
	public static void main(String[] args) throws Exception{
		var simpleJmsProducer = new SimpleJmsTopicConsumer();
		var session = simpleJmsProducer.createSession();
		simpleJmsProducer.receiveMessage(session);
	}
	

}
