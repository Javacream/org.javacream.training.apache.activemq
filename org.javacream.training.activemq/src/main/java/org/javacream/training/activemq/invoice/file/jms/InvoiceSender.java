package org.javacream.training.activemq.invoice.file.jms;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

import org.javacream.training.activemq.invoice.file.InvoiceReader;
import org.javacream.training.activemq.util.jms.JmsBase;
import org.javacream.training.activemq.util.jms.JmsUtil;

public class InvoiceSender extends JmsBase{

	public InvoiceSender(){
		super(false, Session.AUTO_ACKNOWLEDGE);
	}
	
	public static void main(String[] args) {
		new InvoiceSender().execute();
	}
	public void execute(){
		Map<String, Object> invoiceMap = new InvoiceReader().readFile();
		MapMessage mapMessage = JmsUtil.createMapMessage(getSession());
		for (String key: invoiceMap.keySet()){
			try {
				mapMessage.setObjectProperty(key, invoiceMap.get(key));
			} catch (JMSException e) {
				throw new RuntimeException(e);
			}
		}
		JmsUtil.send(getSession(), JmsUtil.createQueue(getSession(), "queue/invoices"), mapMessage);
		close();
	}
	
}
