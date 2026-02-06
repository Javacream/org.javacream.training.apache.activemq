package org.javacream.training.activemq.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
	@JmsListener(destination = "${app.jms.queue-name}")
	public void receive(String message) {
		System.out.println("Received: " + message);
	}
}