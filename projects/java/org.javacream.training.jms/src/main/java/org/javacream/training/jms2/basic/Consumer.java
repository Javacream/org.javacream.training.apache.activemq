package org.javacream.training.jms2.basic;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;


public class Consumer {

	public static void main(String[] args) throws Exception{
		try(var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl)){
			var jmsContext = connectionFactory.createContext(BrokerConfiguration.username, BrokerConfiguration.password);
			var queue = jmsContext.createQueue(BrokerConfiguration.queueName);
			var message = jmsContext.createConsumer(queue).receive();
			System.out.println(message);
		}
	}

}
