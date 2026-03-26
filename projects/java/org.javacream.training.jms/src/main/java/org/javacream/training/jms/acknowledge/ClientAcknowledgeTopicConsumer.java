package org.javacream.training.jms.acknowledge;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.javacream.training.jms.BrokerConfiguration;


public class ClientAcknowledgeTopicConsumer {

	public static void main(String[] args) throws Exception{
		var connectionFactory = new ActiveMQConnectionFactory(BrokerConfiguration.brokerUrl);
		var connection = connectionFactory.createConnection(BrokerConfiguration.username, BrokerConfiguration.password);
		var session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		var destination = session.createTopic(DestinationConfiguration.destinationName);
		var consumer = session.createConsumer(destination);
		connection.start();
		consumer.setMessageListener(new SimpleMessageListener());
		var sync = new Object();
		synchronized (sync) {
			sync.wait();
			
		}
		connection.close();
		connectionFactory.close();
	}

}

class SimpleMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message){
		var textMessage = (TextMessage)message;
		try {
			System.out.println(textMessage.getText());
			if (message.getBooleanProperty("finish")){  
				System.out.println("acknowledge");
				message.acknowledge();
			}else{  
				System.out.println("Message " + message.getJMSMessageID() + "received but not acknowledged" );
			}
				
		}
		catch(JMSException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
