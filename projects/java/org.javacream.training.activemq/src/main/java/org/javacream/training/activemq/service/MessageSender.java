package org.javacream.training.activemq.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {
	private final JmsTemplate jmsTemplate;

	public MessageSender(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void send(String message) {
		jmsTemplate.convertAndSend(message);
	}
}