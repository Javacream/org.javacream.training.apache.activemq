package org.javacream.training.jms;

public interface ApplicationConfiguration {
	String brokerURL = "tcp://localhost:61616";
	String queueName = "exampleQueue";
	String topicName = "exampleTopic";
	String username = "artemis";
	String password = "simetraehcapa";

	String jdbcURL = "jdbc:mysql://localhost:3406/javacream";
    String dbUsername = "user";
    String dbPassword = "user";

}
