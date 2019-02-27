package org.javacream.training.activemq.util.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

public class JmsBase {

	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	
	{
		connectionFactory = JmsUtil.getConnectionFactory();
		try {
			connection = connectionFactory.createConnection();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	public JmsBase(boolean transacted, int acknowledgeMode){
		try {
			session = connection.createSession(transacted, acknowledgeMode);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	public Connection getConnection() {
		return connection;
	}
	public Session getSession() {
		return session;
	}
	
	public void close(){
		try {
			connection.close();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
}
