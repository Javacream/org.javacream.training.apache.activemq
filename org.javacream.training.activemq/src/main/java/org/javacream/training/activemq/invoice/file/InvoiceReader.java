package org.javacream.training.activemq.invoice.file;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class InvoiceReader {

	public Map<String, Object> readFile(){
		Properties props = new Properties();
		try {
			props.load(this.getClass().getResourceAsStream("/data/invoice.dat"));
			HashMap<String, Object> result = new HashMap<String, Object>();
			for (Object key: props.keySet()){
				String keyAsString = key.toString();
				result.put(keyAsString, props.getProperty(keyAsString));
			}
			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
