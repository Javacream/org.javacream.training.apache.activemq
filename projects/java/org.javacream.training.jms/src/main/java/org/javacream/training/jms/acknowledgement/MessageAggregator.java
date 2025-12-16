package org.javacream.training.jms.acknowledgement;

import static org.javacream.training.jms.ApplicationConfiguration.brokerURL;
import static org.javacream.training.jms.ApplicationConfiguration.password;
import static org.javacream.training.jms.ApplicationConfiguration.queueName;
import static org.javacream.training.jms.ApplicationConfiguration.username;

import java.util.LinkedList;
import java.util.List;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageAggregator {

	private List<Message> messages = new LinkedList<Message>();
	public Session createSession() throws Exception{
		var connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		var connection = connectionFactory.createConnection(username, password);
		var session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		connection.start();
		return session;
	}
	
	public Queue getQueue(Session session) throws Exception {
		return session.createQueue(queueName);
	}
	public void receiveMessage(Session session) throws Exception {
		var messageConsumer = session.createConsumer(getQueue(session));
		Message message = null;
		for (int i = 0; i < 3; i++) {
			message = messageConsumer.receive();
			messages.add(message);
		}
		System.out.println(messages);
		message.acknowledge();
		messages.clear();
		
	}
	
	public static void main(String[] args) throws Exception{
		var simpleJmsConsumer = new MessageAggregator();
		var session = simpleJmsConsumer.createSession();
		simpleJmsConsumer.receiveMessage(session);
	}

	
	
}
