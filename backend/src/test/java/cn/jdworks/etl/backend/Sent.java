package cn.jdworks.etl.backend;

import org.junit.*;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.Serializable;

import cn.jdworks.etl.protocol.*;


public class Sent{

	private static String connectionURI = "tcp://127.0.0.1:9100";
	private static String destinationName_EEG = "ETL.EXECUTE.GUID";

	@Test
	public void send() {
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
			System.out.println("Sent Task To Executor!");
			System.out.println("TaskId:" + 10);
			System.out.println("Name: footask.jar");
			System.out.println("args:" + 2);
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
