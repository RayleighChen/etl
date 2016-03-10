package cn.jdworks.etl.executor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import cn.jdworks.etl.executor.util.StreamWatch;
import cn.jdworks.etl.protocol.TaskAssignment;
import cn.jdworks.etl.protocol.TaskReport;

public class TaskReceiver {
	
	private String connectionURI = "tcp://127.0.0.1:9100";
	private String destinationName_EEG = "ETL.EXECUTE.GUID";
	private String destinationName_ER = "ETL.REPORT";
	
	public void listen_task() {
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
			Destination destination_EEG = session
					.createQueue(destinationName_EEG);
			// Create a MessageConsumer from the Session to the Topic or Queue
			MessageConsumer consumer_EEG = session
					.createConsumer(destination_EEG);

			while (true) {
				consumer_EEG.setMessageListener(new MessageListener() {
					@Override
					public void onMessage(Message message) {
						// TextMessage textMsg = (TextMessage) message;
						try {
							System.out.println("###Task Received###");
							if (message instanceof ObjectMessage) {
								ObjectMessage objMsg = (ObjectMessage) message;
								try {
									TaskAssignment obj = (TaskAssignment) objMsg
											.getObject();
									List<String> list = new ArrayList<String>();
									ProcessBuilder pb = null;
									Process p = null;
									// list the files and directorys under C:\
									list.add("java");
									list.add("-jar");
									list.add(obj.getScriptName());
									list.add(Integer.toString(obj.getId()));
									list.add(obj.getScriptArgs());
									
//									list.add("../footask/target/footask-tmp.jar");
//									list.add("1234");
									pb = new ProcessBuilder(list);
									p = pb.start();

									// process error and output message
									StreamWatch errorWatch = new StreamWatch(p.getErrorStream(),
											"ERROR");
									StreamWatch outputWatch = new StreamWatch(p.getInputStream(),
											"OUTPUT");
									// start to watch
									errorWatch.start();
									outputWatch.start();
									// wait for exit
									int exitVal = p.waitFor();
									// print the content from ERROR and OUTPUT									
									
									TaskReport report = new TaskReport();
									report.setId(obj.getId());
									
									if (exitVal == 0) {
										report.setSucceed(true);
									} else {
										report.setSucceed(false);
									}
									report.setLog(outputWatch.getOutput().toString() + outputWatch.getOutput().toString());
									sendReport(report);
//									System.out.println("task_id：" + obj.getid()
//											+ " run:" + obj.getsucceed()
//											+ " log:" + obj.getlog());
									// status = 1;
								} catch (JMSException e) {
									e.printStackTrace();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}
	
	private void sendReport(TaskReport report) {
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
			Destination destination = session.createQueue(destinationName_ER);

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);


			ObjectMessage message = session
					.createObjectMessage((Serializable) report);
			// Tell the producer to send the message
			System.out.println("Sent Report");
			producer.send(message);
			// Clean up
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		new Thread() {
			@Override
			public void run() {
				new TaskReceiver().listen_task();
			}
		}.start();

	}
}
