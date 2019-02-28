package org.javacream.training.app.jms.news;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class NewsMessageListener implements MessageListener {
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println("Received news message, category="
					+ textMessage.getStringProperty("category") + ", text="
					+ textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}