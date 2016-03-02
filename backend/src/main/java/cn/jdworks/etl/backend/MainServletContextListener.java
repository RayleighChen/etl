package cn.jdworks.etl.backend;
import javax.servlet.ServletContext;  
import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;  


import java.net.URI;
import javax.jms.JMSException;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

public class MainServletContextListener implements ServletContextListener {  

    protected BrokerService createBroker() throws JMSException {
        BrokerService broker = null;
	
        try {
            broker = BrokerFactory.createBroker(new URI("broker://()/localhost"));
            broker.setBrokerName("DefaultBroker");
            broker.addConnector("tcp://localhost:9100");
            broker.setUseShutdownHook(false);
            
            broker.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return broker;
    }
   
    public void contextInitialized(ServletContextEvent event){
        ServletContext sc = event.getServletContext();  

	try{
	    // This systemproperty is used if we dont want to
	    // have persistence messages as a default
	    System.setProperty("activemq.persistenceAdapter",
			       "org.apache.activemq.store.vm.VMPersistenceAdapter");
	    // create and start ActiveMQ broker
	    BrokerService broker = createBroker();
	    sc.setAttribute("broker", broker);
	    System.out.println("broker start.");  
	}catch( Exception e){
	    e.printStackTrace();
	}
    }

    // 应用监听器的销毁方法  
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();  

	try{
	    BrokerService broker = (BrokerService)sc.getAttribute("broker");
	    broker.stop();
	    broker.waitUntilStopped();
	    sc.removeAttribute("broker");
	    System.out.println("broker stop.");
	}catch( Exception e){
	    e.printStackTrace();
	}
    }
}
