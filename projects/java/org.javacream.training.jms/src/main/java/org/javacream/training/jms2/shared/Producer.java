package org.javacream.training.jms2.shared;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;

public class Producer {

	public static void main(String[] args){
		try(var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl)){
			var jmsContext = connectionFactory.createContext(BrokerConfiguration.username, BrokerConfiguration.password);
			var topic = jmsContext.createTopic(Configuration.topicName);
			for (int i = 0; i < 5; i++) {
				jmsContext.createProducer().send(topic, "Hello" + i);
			}
		}
		
	}

}
