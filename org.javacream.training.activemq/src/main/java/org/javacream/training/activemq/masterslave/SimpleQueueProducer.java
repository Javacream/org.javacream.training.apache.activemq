package org.javacream.training.activemq.masterslave;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.javacream.training.util.jms.JmsUtil;

public class SimpleQueueProducer {

	public static void main(String[] args) throws Exception {
		new SimpleQueueProducer().testJms();
	}

	public void testJms() throws Exception {
		String url = "failover:(tcp://localhost:61616,tcp://localhost:51515)?initialReconnectDelay=100";
		ConnectionFactory connectionFactory = JmsUtil.getConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("JAVACREAM.QUEUE.A");
		Message request = session.createMessage();
		request.setStringProperty("param", "Hello!");
		MessageProducer messageProducer = session.createProducer(destination);
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(() -> {
			try {
				messageProducer.send(request);
			} catch (JMSException e) {
				System.err.println("Exception sending message: " + e.getMessage());
			}
		}, 0, 4, TimeUnit.SECONDS);
	} 

}
