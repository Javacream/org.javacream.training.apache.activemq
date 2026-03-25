package org.javacream.training.jms;


public interface BrokerConfiguration {

	String brokerUrl = "tcp://localhost:61616";
	String queueName = "exampleQueue";
	String topicName = "exampleTopic";
	String username = "artemis";
	String password = "simetraehcapa";
}
