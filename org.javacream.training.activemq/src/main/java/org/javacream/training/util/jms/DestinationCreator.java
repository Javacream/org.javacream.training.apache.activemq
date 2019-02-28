package org.javacream.training.util.jms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Session;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName="javacream:type=util,name=destinationCreator")
public class DestinationCreator {

	
	@ManagedOperation
	public void createDestinations() {
		try {
			ConnectionFactory connectionFactory = JmsUtil
					.getConnectionFactory("tcp://localhost:61616");
			Connection connection = connectionFactory.createConnection();

			connection.start();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("c:/_training/topics.dat")));
			String topicName = "";
			while((topicName = bufferedReader.readLine()) != null){
				Destination destination = session.createTopic(topicName);
				session.createConsumer(destination).close();
				
			}
			
			bufferedReader.close();
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("c:/_training/queues.dat")));
			String queueName = "";
			while((queueName = bufferedReader.readLine()) != null){
				Destination destination = session.createQueue(queueName);
				session.createConsumer(destination).close();
			}
			
			bufferedReader.close();
			session.close();
			connection.close();
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void main(String[] args) {
		new DestinationCreator().createDestinations();
	}
}
