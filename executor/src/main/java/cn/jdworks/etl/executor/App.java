package cn.jdworks.etl.executor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import cn.jdworks.etl.executor.monitor.IMonitorService;
import cn.jdworks.etl.executor.monitor.MonitorInfoBean;
import cn.jdworks.etl.executor.monitor.MonitorServiceImpl;
import cn.jdworks.etl.protocol.ExecutorHeartbeat;
import cn.jdworks.etl.protocol.TaskStats;

public class App {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(App.class);

	private String connectionURI = "tcp://127.0.0.1:9100";
	private String destinationName_EH = "ETL.HEARYBEAT";
	private Hashtable<Integer, TaskStats> tasks;
	private TimerHeartBeat timerHeartBeat;
	private ExecutorHeartbeat heartbeat;
	private TaskReceiver taskReceiver;
	private StatsReceiver statsReceiver;
	private Timer timer;

	public App() {
		timer = new Timer();
		heartbeat = new ExecutorHeartbeat();
		tasks = new Hashtable<Integer, TaskStats>();
		timerHeartBeat = new TimerHeartBeat(connectionURI, destinationName_EH, heartbeat, tasks);
		timer.schedule(timerHeartBeat, 0, 1000);
		
		taskReceiver = new TaskReceiver();
		new Thread() {
			@Override
			public void run() {
				taskReceiver.listen_task();
			}
		}.start();
		
		statsReceiver = new StatsReceiver();
		new Thread() {
			@Override
			public void run() {
				while(true) {
					tasks.put(statsReceiver.recive().getId(), statsReceiver.recive());
				}
			}
		}.start();
		
	}
	
	public static void main(String[] args) throws Exception {
		App app = new App();
	}
}

class TimerHeartBeat extends TimerTask {

	private String connectionURI;
	private String destinationName;
	private Hashtable<Integer, TaskStats> tasks;
	private ExecutorHeartbeat heartbeat;

	public TimerHeartBeat(String connectionURI, String destinationName,
			ExecutorHeartbeat heartbeat, Hashtable<Integer, TaskStats> tasks) {
		super();
		this.connectionURI = connectionURI;
		this.destinationName = destinationName;
		this.tasks = tasks;
		this.heartbeat = heartbeat;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("---Sent HeartBeat---");
		send_heartbeat();
	}

	private void send_heartbeat() {
		try {
			IMonitorService service = new MonitorServiceImpl();
			MonitorInfoBean monitorInfo = service.getMonitorInfoBean();

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
			Destination destination_eh = session.createQueue(destinationName);

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer_eh = session
					.createProducer(destination_eh);
			producer_eh.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// Create a messages

			heartbeat.setCpuLoad(monitorInfo.getCpuRatio());
			float mem = (float) monitorInfo.getUsedMemory()
					/ monitorInfo.getTotalMemorySize() * 1.0f;

			BigDecimal b = new BigDecimal(mem); 
			float memRatio = b.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue() * 100; 

			heartbeat.setMemLoad(memRatio);
			heartbeat.setTasks(tasks);

			ObjectMessage message = session
					.createObjectMessage((Serializable) heartbeat);
			producer_eh.send(message);

			// Clean up
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	public Hashtable<Integer, TaskStats> getTasks() {
		return tasks;
	}

	public void setTasks(Hashtable<Integer, TaskStats> tasks) {
		this.tasks = tasks;
	}
	
}