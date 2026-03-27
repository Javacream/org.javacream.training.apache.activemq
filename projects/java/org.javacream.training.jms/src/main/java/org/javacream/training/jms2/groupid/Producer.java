package org.javacream.training.jms2.groupid;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;

public class Producer {

	public static void main(String[] args){
		try(var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl)){
			var jmsContext = connectionFactory.createContext(BrokerConfiguration.username, BrokerConfiguration.password);
			var queue = jmsContext.createQueue(Configuration.queueName);
			for (int i = 0; i < 5; i++) {
				jmsContext.createProducer().setProperty(Configuration.GROUP_ID_HEADER, Configuration.groupId).send(queue, "Hello" + i);
			}
			jmsContext.createProducer().setProperty(Configuration.GROUP_ID_HEADER, Configuration.groupId).setProperty(Configuration.GROUP_SEQ_HEADER, -1).send(queue, "Hello final");
			for (int i = 5; i < 10; i++) {
				jmsContext.createProducer().send(queue, "Hello" + i);
			}
		}
		
	}

}
