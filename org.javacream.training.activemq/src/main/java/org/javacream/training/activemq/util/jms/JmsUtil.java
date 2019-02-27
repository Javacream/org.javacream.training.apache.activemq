package org.javacream.training.activemq.util.jms;

import javax.jms.BytesMessage;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public abstract class JmsUtil {

	public static final String URL = "tcp://localhost:61616";
	private static ActiveMQConnectionFactory factory;

	public static ConnectionFactory getConnectionFactory() {
		if (factory == null) {
			factory = new ActiveMQConnectionFactory(URL);
		}
		return factory;

	}
	public static ConnectionFactory getConnectionFactory(String url) {
		return  new ActiveMQConnectionFactory(url);

	}
	
	public static void send(Session session, Destination destination, Message message){
		MessageProducer producer;
		try {
			producer = session.createProducer(destination);
			producer.send(message);
			producer.close();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		
	}

	public static void setListener(Session session, Destination destination, MessageListener messageListener){
		try {
			session.createConsumer(destination).setMessageListener(messageListener);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	public static void setListener(Session session, Destination destination, MessageListener messageListener, String messageSelector) {
		try {
			session.createConsumer(destination, messageSelector).setMessageListener(messageListener);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Message createMessage(Session session){
		try {
			return session.createMessage();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	public static TextMessage createTextMessage(Session session){
		try {
			return session.createTextMessage();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	public static ObjectMessage createObjectMessage(Session session){
		try {
			return session.createObjectMessage();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	public static MapMessage createMapMessage(Session session){
		try {
			return session.createMapMessage();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	public static BytesMessage createBytesMessage(Session session){
		try {
			return session.createBytesMessage();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	public static StreamMessage createStreamMessage(Session session){
		try {
			return session.createStreamMessage();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Queue createQueue(Session session, String name){
		try {
			return session.createQueue(name);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		
	}
	public static Topic createTopic(Session session, String name){
		try {
			return session.createTopic(name);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		
	}




}
