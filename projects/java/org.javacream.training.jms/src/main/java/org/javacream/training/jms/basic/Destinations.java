package org.javacream.training.jms.basic;

import static org.javacream.training.jms.ApplicationConfiguration.brokerURL;
import static org.javacream.training.jms.ApplicationConfiguration.password;
import static org.javacream.training.jms.ApplicationConfiguration.queueName;
import static org.javacream.training.jms.ApplicationConfiguration.topicName;
import static org.javacream.training.jms.ApplicationConfiguration.username;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class Destinations {

	public static void main(String[] args) throws Exception {

		Connection connection = null;
		Session session = null;

		try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL)) {
			connection = connectionFactory.createConnection(username, password);
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue queue = session.createQueue(queueName);
			Topic topic = session.createTopic(topicName);
			System.out.println(queue);
			MessageProducer messageProducer = session.createProducer(queue);
			TextMessage textMessage = session.createTextMessage("Hugo");
			messageProducer.send(textMessage);
//			var consumer = session.createConsumer(queue);
//			var received = consumer.receive();
//			received.acknowledge();
//			System.out.println(received);
		}

	}
}