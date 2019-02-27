
package org.javacream.training.activemq.news;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.javacream.training.activemq.util.jms.JmsUtil;

public class NewsConsumer {

	public static void main(String[] args) {
		String category = "politics";
		if (args.length > 0){
			category = args[0];
		}
		try{
			ConnectionFactory factory = JmsUtil.getConnectionFactory();
			Connection conn = factory.createConnection();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("newsTopic");
			MessageConsumer consumer = session.createConsumer(destination, "category='" + category + "'");
			conn.start();
			NewsMessageListener demoMessageListener = new NewsMessageListener();
			consumer.setMessageListener(demoMessageListener);
			System.out.println("NewsMessageListener with category " + category + " ready.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
