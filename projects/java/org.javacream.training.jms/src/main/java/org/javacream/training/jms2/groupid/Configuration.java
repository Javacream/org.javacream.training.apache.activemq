package org.javacream.training.jms2.groupid;

public interface Configuration {

	String queueName = "demo.queue";
	String groupId = "Javacream";
	String GROUP_ID_HEADER = "JMSXGroupID";
	String GROUP_SEQ_HEADER = "JMSXGroupSeq";
	
}
