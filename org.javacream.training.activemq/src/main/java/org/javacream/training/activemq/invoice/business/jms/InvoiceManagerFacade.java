package org.javacream.training.activemq.invoice.business.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

import org.javacream.training.activemq.invoice.business.Customer;
import org.javacream.training.activemq.invoice.business.Invoice;
import org.javacream.training.activemq.invoice.business.InvoiceManager;
import org.javacream.training.activemq.util.jms.BaseMessageListener;
import org.javacream.training.activemq.util.jms.JmsBase;
import org.javacream.training.activemq.util.jms.JmsUtil;

public class InvoiceManagerFacade  extends JmsBase {

	private InvoiceManager invoiceManager;
	public InvoiceManagerFacade(){
		super(false, Session.AUTO_ACKNOWLEDGE);
		JmsUtil.setListener(getSession(), JmsUtil.createQueue(getSession(), "queue/invoices"), new InvoiceMessageListener());
		try {
			getConnection().start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		invoiceManager = new InvoiceManager();
	}

	public static void main(String[] args) {
		new InvoiceManagerFacade();
	}
	
	private class InvoiceMessageListener extends BaseMessageListener{

		@Override
		protected void handleMapMessage(MapMessage message) {
			try {
				Customer customer = new Customer(message.getStringProperty("name"), message.getStringProperty("address"));
				long productId = Long.parseLong(message.getStringProperty("productId"));
				int amount = Integer.parseInt(message.getStringProperty("amount"));
				double totalPrice = Double.parseDouble(message.getStringProperty("totalPrice"));
				Invoice invoice = new Invoice(customer, productId, amount, totalPrice);
				invoiceManager.create(invoice);
			} catch (JMSException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
