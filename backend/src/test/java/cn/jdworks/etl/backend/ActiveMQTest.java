package cn.jdworks.etl.backend;

import java.net.URI;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import org.junit.*;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import java.io.Serializable;
import cn.jdworks.etl.protocol.*;

public class ActiveMQTest implements ExceptionListener {

	private BrokerService broker;
	private String connectionURI = "tcp://localhost:9100";
	private String destinationName = "foo";

	@Before
	public void setUp() throws Exception {
		try {
			// This systemproperty is used if we dont want to
			// have persistence messages as a default
			System.setProperty("activemq.persistenceAdapter",
					"org.apache.activemq.store.vm.VMPersistenceAdapter");
			System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");
			// create and start ActiveMQ broker
			this.broker = createBroker();
			System.out.println("broker start.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		try {
			this.broker.stop();
			this.broker.waitUntilStopped();
			System.out.println("broker stop.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected BrokerService createBroker() throws JMSException {
		BrokerService broker = null;

		try {
			broker = BrokerFactory
					.createBroker(new URI("broker://()/localhost"));
			broker.setBrokerName("DefaultBroker");
			broker.addConnector("tcp://localhost:9100");
			broker.setUseShutdownHook(false);

			broker.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return broker;
	}

	@Test
	public void SendAndReceive() {
		Send();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
		;
		Receive();
	}

	private void Send() {
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
			Destination destination = session.createQueue(destinationName);

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// Create a messages
			// String text = "Hello world! From: " +
			// Thread.currentThread().getName() + " : " + this.hashCode();
			// Person person = new Person(23, "阿三");
			ExecutorHeartbeat obj = new ExecutorHeartbeat("xxx", 2.0, 3.3);
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

	private void Receive() {
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					connectionURI);
			connectionFactory.setTrustAllPackages(true);

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			connection.setExceptionListener(this);

			// Create a Session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue(destinationName);

			// Create a MessageConsumer from the Session to the Topic or Queue
			MessageConsumer consumer = session.createConsumer(destination);

			// Wait for a message
			Message message = consumer.receive(1000);

			/*
			 * if (message instanceof TextMessage) { TextMessage textMessage =
			 * (TextMessage) message; String text = textMessage.getText();
			 * System.out.println("Received: " + text); } else {
			 * System.out.println("Received: " + message); }
			 */

			if (message instanceof ObjectMessage) {
				ObjectMessage objMsg = (ObjectMessage) message;
				try {
					ExecutorHeartbeat obj = (ExecutorHeartbeat) objMsg
							.getObject();
					System.out.println("IP：" + obj.getIpAddr() + " cpu:"
							+ obj.getCpuLoad() + " mem:" + obj.getMemLoad());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

			consumer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	public synchronized void onException(JMSException ex) {
		System.out.println("JMS Exception occured.  Shutting down client.");
	}
}
