package org.javacream.training.activemq.redelivery;

public interface TransactionalConstants {

	String DESTINATION_AGGREGATOR = "JAVACREAM.TRANSACTIONAL.DLQ.AGGREGATOR";
	String DESTINATION_CONSUMER = "JAVACREAM.TRANSACTIONAL.DLQ.CONSUMER";
}
