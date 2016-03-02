package cn.jdworks.etl.backend.biz;

import org.apache.activemq.ActiveMQConnectionFactory;


public class ExecutorManager{

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ExecutorManager.class);
    
    public ExecutorManager() throws Exception{
	LOG.info("Executor Manager created.");

	try{
	    // start a client
	    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:9100");
	    factory.createConnection();
	    LOG.info("Executor manager connected ActiveMQ.");
	}catch(Exception e){
	    LOG.fatal(e);
	    throw e;
	}
    }

    private int i = 0;
    public void foo()
    {
	LOG.info(i++);
    }
}

