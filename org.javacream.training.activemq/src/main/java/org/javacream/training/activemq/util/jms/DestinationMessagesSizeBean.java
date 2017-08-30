package org.javacream.training.activemq.util.jms;

import java.io.Serializable;
import java.util.Enumeration;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName="javacream:type=util,name=destinationPayloadSize")
public class DestinationMessagesSizeBean {

	private String queueName;
	private String url = "tcp://localhost:61616";

	@ManagedAttribute(description = "activemq url")
	public String getUrl() {
		return url;
	}

	@ManagedAttribute(description = "activemq url")
	public void setUrl(String url) {
		this.url = url;
	}

	@ManagedAttribute(description = "name of destination to browse")
	public String getQueueName() {
		return queueName;
	}

	@ManagedAttribute(description = "activemq url")
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	@ManagedOperation
	public long calculateMessageSize() {
		try {
			ConnectionFactory connectionFactory = JmsUtil
					.getConnectionFactory(url);
			Connection connection = connectionFactory.createConnection();

			connection.start();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			QueueBrowser browser = session.createBrowser(
					JmsUtil.createQueue(session, queueName));
			Enumeration<?> messages = browser.getEnumeration();
			long payloadSize = 0;
			while (messages.hasMoreElements()) {
				Message message = (Message) messages.nextElement();
				if (message instanceof TextMessage){
					payloadSize += ((TextMessage)message).getText().length();
				}
				if (message instanceof BytesMessage){
					payloadSize += ((BytesMessage)message).getBodyLength();
				}
				if (message instanceof ObjectMessage){
					Serializable obj = ((ObjectMessage)message).getObject();
					payloadSize += SerializationUtils.serialize(obj).length;
				}
				else{
					ActiveMQMessage am = (ActiveMQMessage) message;
					payloadSize += am.getSize();
				}
				
			}
			browser.close();
			connection.close();
			return payloadSize;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		DestinationMessagesSizeBean bean = new DestinationMessagesSizeBean();
		bean.setUrl("tcp://localhost:61616");
		bean.setQueueName("queue/Demo");
		System.out.println("Payload-Bytes in Queue " + bean.getQueueName() + ": " + bean.calculateMessageSize());
	}
}
