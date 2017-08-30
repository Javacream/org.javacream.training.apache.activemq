package org.javacream.training.activemq.util.jms;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

public abstract class BaseMessageListener implements MessageListener {

	@Override
	public final void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				handleTextMessage((TextMessage) message);
				return;
			}
			if (message instanceof StreamMessage) {
				handleStreamMessage((StreamMessage) message);
				return;
			}
			if (message instanceof BytesMessage) {
				handleBytesMessage((BytesMessage) message);
				return;
			}
			if (message instanceof MapMessage) {
				handleMapMessage((MapMessage) message);
				return;
			}
			if (message instanceof ObjectMessage) {
				handleObjectMessage((ObjectMessage) message);
				return;
			}
			handleMessage(message);


		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	protected void handleMessage(Message message) throws JMSException {
		throw new UnsupportedOperationException(
				"handling raw messages not supported");
	}

	protected void handleTextMessage(TextMessage message) throws JMSException {
		throw new UnsupportedOperationException(
				"handling TextMessage not supported");
	}
	protected void handleMapMessage(MapMessage message) throws JMSException {
		throw new UnsupportedOperationException(
				"handling MapMessage not supported");
	}

	protected void handleBytesMessage(BytesMessage message) throws JMSException {
		throw new UnsupportedOperationException(
				"handling BytesMessage not supported");
	}

	protected void handleStreamMessage(StreamMessage message) throws JMSException {
		throw new UnsupportedOperationException(
				"handling StreamMessage not supported");
	}

	protected void handleObjectMessage(ObjectMessage message) throws JMSException {
		throw new UnsupportedOperationException(
				"handling ObjectMessage not supported");
	}

}
