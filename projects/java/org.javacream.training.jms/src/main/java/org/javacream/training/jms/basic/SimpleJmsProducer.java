package org.javacream.training.jms.basic;

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

}
