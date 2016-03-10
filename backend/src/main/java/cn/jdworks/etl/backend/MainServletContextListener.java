package cn.jdworks.etl.backend;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.net.URI;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.nutz.ioc.loader.annotation.Inject;

import cn.jdworks.etl.backend.biz.ExecutorManager;


public class MainServletContextListener implements ServletContextListener,
		ExceptionListener {

	private ExecutorManager executorManager;
	private String connectionURI = "tcp://127.0.0.1:9100";

	protected BrokerService createBroker() throws JMSException {
		BrokerService broker = null;

		try {
			executorManager = new ExecutorManager();
			broker = BrokerFactory
					.createBroker(new URI("broker://()/127.0.0.1"));
			broker.setBrokerName("DefaultBroker");
			broker.addConnector(connectionURI);
			broker.setUseShutdownHook(false);
			broker.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return broker;
	}

	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();

		try {
			System.setProperty("activemq.persistenceAdapter",
					"org.apache.activemq.store.vm.VMPersistenceAdapter");
			// create and start ActiveMQ broker
			BrokerService broker = createBroker();
			sc.setAttribute("broker", broker);
			System.out.println("broker start.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 应用监听器的销毁方法
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();

		try {
			BrokerService broker = (BrokerService) sc.getAttribute("broker");
			broker.stop();
			broker.waitUntilStopped();
			sc.removeAttribute("broker");
			System.out.println("broker stop.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onException(JMSException arg0) {
		// TODO Auto-generated method stub

	}
}
