package cn.jdworks.etl.backend;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import cn.jdworks.etl.protocol.TaskAssignment;

public class Task {
	private static String connectionURI = "tcp://127.0.0.1:9100";
	private static String destinationName_EEG = "ETL.EXECUTE.GUID";
	
	@Test
	public static void main(String[] args) {
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					connectionURI);
			connectionFactory.setTrustAllPackages(true);

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue(destinationName_EEG);

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			// Create a messages
			TaskAssignment obj = new TaskAssignment(10, "../footask/target/footask-tmp.jar",
					"2");// 任务分配
			ObjectMessage message = session
					.createObjectMessage((Serializable) obj);
			// Tell the producer to send the message
			System.out.println("Sent message: " + message.hashCode());
			producer.send(message);
			// Clean up
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}
}
