package org.javacream.training.jms.basic;

import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import static org.javacream.training.jms.ApplicationConfiguration.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class SimpleJmsConsumer {
	
	public Session createSession() throws Exception{
		var connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		var connection = connectionFactory.createConnection(username, password);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		connection.start();
		return session;
	}
	
	public Queue getQueue(Session session) throws Exception {
		return session.createQueue(queueName);
	}
	
	public void receiveMessage(Session session) throws Exception {
		var messageConsumer = session.createConsumer(getQueue(session));
		var message = messageConsumer.receive();
		var textMessage = (TextMessage) message;
		var text = textMessage.getText();
		System.out.println(text);
	}
	
	public static void main(String[] args) throws Exception{
		var simpleJmsProducer = new SimpleJmsConsumer();
		var session = simpleJmsProducer.createSession();
		simpleJmsProducer.receiveMessage(session);
	}
	

}
