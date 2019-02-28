
package org.javacream.training.app.jms.news;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.javacream.training.util.jms.JmsUtil;

public class NewsProducer {

	public static void main(String[] args) {
		try{
			int counter = 0;
			ConnectionFactory factory = JmsUtil.getConnectionFactory();
			Connection conn = factory.createConnection();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("newsTopic");
			MessageProducer producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage();
			int i = 1;
			while(i == 1){
				try{
					Thread.sleep(5000);
				}
				catch(InterruptedException ie){
					
				}
				switch(counter){
					case 0: {
						message.setStringProperty("category", "sports");
						message.setText("TSV 1860 zum 42. mal Deutscher Ehren-Fu�ballmeister!");
						break;
					}
					case 1: {
						message.setStringProperty("category", "science");
						message.setText("Neue Wolpertinger-Art entdeckt");
						break;
					}
					case 2: {
						message.setStringProperty("category", "politics");
						message.setText("George W. Bush und Edmund Stoiber von Au�erirdischen entf�hrt");
						counter = -1;
					}
				}
				counter ++;
				System.out.println("Sending " + message.getStringProperty("category") + " message: " + message.getText());
				producer.send(message);
			}
			producer.close();
			session.close();
			conn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
