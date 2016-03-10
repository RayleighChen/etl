package cn.jdworks.etl.backend.biz;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import cn.jdworks.etl.protocol.ExecutorHeartbeat;
import cn.jdworks.etl.protocol.TaskAssignment;
import cn.jdworks.etl.protocol.TaskReport;
import cn.jdworks.etl.protocol.TaskStats;

public class ExecutorManager {

	private String connectionURI = "tcp://127.0.0.1:9100";
	private String destinationName_EH = "ETL.HEARYBEAT";
	private String destinationName_EEG = "ETL.EXECUTE.GUID";
	private String destinationName_ER = "ETL.REPORT";

	
	
	
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(ExecutorManager.class);

	public ExecutorManager() throws Exception {
		LOG.info("Executor Manager created.");
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				listen_heartbeat();
			}
		}).start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				listen_report();
			}
		}).start();		
	}

	private void send_task() {
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					connectionURI);
			connectionFactory.setTrustAllPackages(true);

			Connection connection = connectionFactory.createConnection();
			connection.start();

			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createQueue(destinationName_EEG);

			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			// Create a messages
			TaskAssignment obj = new TaskAssignment(1, "scriptName",
					"scriptArgs");
			ObjectMessage message = session
					.createObjectMessage((Serializable) obj);

			System.out.println("Sent Task TO Executor!");
			producer.send(message);
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	private void listen_heartbeat() {
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					connectionURI);
			connectionFactory.setTrustAllPackages(true);
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination_EH = session
					.createQueue(destinationName_EH);

			MessageConsumer consumer_EH = session
					.createConsumer(destination_EH);
			while (true) {
				consumer_EH.setMessageListener(new MessageListener() {
					@Override
					public void onMessage(Message message) {
						// TextMessage textMsg = (TextMessage) message;
						try {
							System.out.println("---Executor HeartBeat Received---");
							if (message instanceof ObjectMessage) {
								ObjectMessage objMsg = (ObjectMessage) message;
								try {
									ExecutorHeartbeat obj = (ExecutorHeartbeat) objMsg
											.getObject();
									System.out.println("Id: " + obj.getIpAddr());
									System.out.println("guid：" + obj.getId()
											+ " cpu:" + obj.getCpuLoad()
											+ " mem:" + obj.getMemLoad());
									Hashtable<Integer, TaskStats> tasks = obj.getTasks();
									if(tasks != null) {
								        Set<Integer> keys = tasks.keySet();  
								        for(Integer key: keys){  
								        	TaskStats stats = tasks.get(key);
								            System.out.println("TaskId: "+key+" ThreadNum: "+ stats.getThreadNum());
								            Set<String> dbkeys = stats.getDbConns().keySet();
								            for(String db: dbkeys) {
								            	System.out
														.println("ConnectDB: " + stats.getDbConns().get(db) + " DBName: " + db);
								            }
								        }	
									}
									
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

	private void listen_report() {
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
			Destination destination_ER = session
					.createQueue(destinationName_ER);
			// Create a MessageConsumer from the Session to the Topic or Queue
			MessageConsumer consumer_ER = session
					.createConsumer(destination_ER);

			while (true) {
				consumer_ER.setMessageListener(new MessageListener() {
					@Override
					public void onMessage(Message message) {
						// TextMessage textMsg = (TextMessage) message;
						try {
							System.out.println("***TaskReport Received***");
							if (message instanceof ObjectMessage) {
								ObjectMessage objMsg = (ObjectMessage) message;
								try {
									TaskReport obj = (TaskReport) objMsg
											.getObject();
									System.out.println("task_id：" + obj.getId()
											+ " run:" + obj.getSucceed()
											+ " log:" + obj.getLog());
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

	private int i = 0;

	public void foo() {
		LOG.info(i++);
	}
}


class ExecuteTask extends Thread {
	public void run() {
		System.out.println(123);
	}
}
