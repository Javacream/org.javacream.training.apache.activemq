package org.javacream.training.jms2.groupid;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;


public class Consumer {

	public static void main(String[] args) throws Exception{
		try(var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl)){
			var jmsContext = connectionFactory.createContext(BrokerConfiguration.username, BrokerConfiguration.password);
			var queue = jmsContext.createQueue(Configuration.queueName);
			jmsContext.createConsumer(queue).setMessageListener(m -> { 
				var textMessage = (TextMessage)m;			
				try {
					System.out.println(textMessage.getText());
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
