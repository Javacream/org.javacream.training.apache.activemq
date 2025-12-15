package org.javacream.training.jms.basic;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import static org.javacream.training.jms.ApplicationConfiguration.*;
public class TransactionalConsumer{

    public static void main(String[] args) {

        // JMS Connection, Session, and Consumer
        javax.jms.Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;

		try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL)) {
            connection = connectionFactory.createConnection(username, password);
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            consumer = session.createConsumer(queue);
            System.out.println("Waiting for messages from the queue...");
            Message message = consumer.receive(); // This will block until a message is received

            if (message instanceof TextMessage) {
                String messageText = ((TextMessage) message).getText();
                System.out.println("Received Message: " + messageText);
                saveMessageToDatabase(messageText);
            } else {
                System.out.println("Received non-text message");
            }

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                // Clean up resources
                if (consumer != null) {
                    consumer.close();
                }
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveMessageToDatabase(String message) {
        java.sql.Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword);
            String sql = "INSERT INTO MESSAGES (message) VALUES (?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, message);

            // Step 3: Execute the insert query
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Inserted message into database. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Clean up the resources
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
