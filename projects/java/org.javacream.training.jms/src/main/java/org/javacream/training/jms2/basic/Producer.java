package org.javacream.training.jms2.basic;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;


public class Producer {

	public static void main(String[] args){
		try(var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl)){
			var jmsContext = connectionFactory.createContext(BrokerConfiguration.username, BrokerConfiguration.password);
			var queue = jmsContext.createQueue(BrokerConfiguration.queueName);
			jmsContext.createProducer().send(queue, "Hello");
		}
		
	}

}
