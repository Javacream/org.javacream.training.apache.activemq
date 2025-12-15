package org.javacream.training.jms.basic;

import javax.jms.Queue;
import javax.jms.Session;
import static org.javacream.training.jms.ApplicationConfiguration.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class SimpleJmsProducer {
	
	public Session createSession() throws Exception{
		var connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		var connection = connectionFactory.createConnection(username, password);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		return session;
	}
	
	public Queue getQueue(Session session) throws Exception {
		return session.createQueue(queueName);
	}
	
	public void sendMessage(Session session, String text) throws Exception {
		var message = session.createTextMessage(text);
		var messageProducer = session.createProducer(getQueue(session));
		messageProducer.send(message);
	}
	
	public static void main(String[] args) throws Exception{
		var simpleJmsProducer = new SimpleJmsProducer();
		var session = simpleJmsProducer.createSession();
		simpleJmsProducer.sendMessage(session, "Hugo");
	}
	

}
