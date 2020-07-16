
package org.javacream.training.jms.messageselector;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;

import org.javacream.training.util.jms.JmsUtil;

public class DurableNewsConsumer {

	public static void main(String[] args) {
		String category = "politics";
		if (args.length > 0){
			category = args[0];
		}
		try{
			ConnectionFactory factory = JmsUtil.getConnectionFactory();
			Connection conn = factory.createConnection();
			conn.setClientID("sawitzki");
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("JAVACREAM.TOPIC.NEWSTOPIC");
			String subscriptionId="news";
			String messageSelector = "category='" + category + "'";
			MessageConsumer consumer = session.createDurableSubscriber(topic, subscriptionId, messageSelector, true);
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
