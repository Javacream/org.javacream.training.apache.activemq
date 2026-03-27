package org.javacream.training.jms2.shared;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;


public class Consumer1 {
	private static String subscriberId = "Consumer1";
	public static void main(String[] args) throws Exception{
		try(var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl)){
			var jmsContext = connectionFactory.createContext(BrokerConfiguration.username, BrokerConfiguration.password);
			var topic = jmsContext.createTopic(Configuration.topicName);
			jmsContext.createSharedConsumer(topic, subscriberId).setMessageListener(m -> { 
				var textMessage = (TextMessage)m;			
				try {
					System.out.println("Consumer1_1: " + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			});
			jmsContext.createSharedConsumer(topic, subscriberId).setMessageListener(m -> { 
				var textMessage = (TextMessage)m;			
				try {
					System.out.println("Consumer1_2: " + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			});
			jmsContext.createSharedConsumer(topic, subscriberId).setMessageListener(m -> { 
				var textMessage = (TextMessage)m;			
				try {
					System.out.println("Consumer1_3: " + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			});
			synchronized(jmsContext) {
				jmsContext.wait();
			}
		}
	}

}
