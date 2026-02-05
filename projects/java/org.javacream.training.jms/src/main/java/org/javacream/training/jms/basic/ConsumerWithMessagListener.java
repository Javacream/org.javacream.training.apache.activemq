package org.javacream.training.jms.basic;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;


public class ConsumerWithMessagListener {

	public static void main(String[] args) throws Exception{
		var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl);
		var connection = connectionFactory.createConnection(BrokerConfiguration.username, BrokerConfiguration.password);
		var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		var destination = session.createQueue(BrokerConfiguration.queueName);
		//var topic = session.createTopic(BrokerConfiguration.topicName);
		var selector = "hugo = 'Emil' and this='that'";
		var consumer = session.createConsumer(destination, selector);
		connection.start();
		consumer.setMessageListener(new MyListener());
		var sync = new Object();
		synchronized(sync) {
			sync.wait();
		}
		//connection.close();
		//connectionFactory.close();
	}

	static class MyListener implements MessageListener{

		@Override
		public void onMessage(Message message) {
			var textMessage = (TextMessage)message;
			try {
				System.out.println(textMessage.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
	}
}
