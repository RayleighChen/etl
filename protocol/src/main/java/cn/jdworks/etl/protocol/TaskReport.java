package cn.jdworks.etl.protocol;

import java.io.Serializable;

public class TaskReport implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2566856946706200759L;
	/**
	 * 
	 */
    private int id;
    private boolean succeed;
    private String log;
 
    public TaskReport() {
       super();
    }
 
    public TaskReport(int id, boolean succeed, String log) {
        super();
        this.id = id;
        this.succeed = succeed;
        this.log = log;
    }


    /**
     * Set the value of id.
     **/
    public void setId(int id) {
	this.id = id;
    }

    /**
     * Get the value of id.
     **/
    public int getId() {
	return id;
    }
    /**
     * Set the value of succeed.
     **/
    public void setSucceed(boolean succeed) {
	this.succeed = succeed;
    }

    /**
     * Get the value of succeed.
     **/
    public boolean getSucceed() {
	return succeed;
    }
    /**
     * Set the value of log.
     **/
    public void setLog(String log) {
	this.log = log;
    }

    /**
     * Get the value of log.
     **/
    public String getLog() {
	return log;
    }
}
