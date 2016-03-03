package cn.jdworks.etl.protocol;

import java.io.Serializable;
import java.util.Hashtable;

public class ExecutorHeartbeat implements Serializable{
    private String ipAddr;
    private double cpuLoad;
    private double memLoad;
 
    public ExecutorHeartbeat() {
       super();
    }
 
    public ExecutorHeartbeat(String ipAddr, double cpuLoad, double memLoad) {
        super();
        this.ipAddr = ipAddr;
        this.cpuLoad = cpuLoad;
	this.memLoad = memLoad;
    }


    /**
     * Set the value of ipAddr.
     **/
    public void setIpAddr(String ipAddr) {
	this.ipAddr = ipAddr;
    }

    /**
     * Get the value of ipAddr.
     **/
    public String getIpAddr() {
	return ipAddr;
    }
    /**
     * Set the value of cpuLoad.
     **/
    public void setCpuLoad(double cpuLoad) {
	this.cpuLoad = cpuLoad;
    }

    /**
     * Get the value of cpuLoad.
     **/
    public double getCpuLoad() {
	return cpuLoad;
    }
    /**
     * Set the value of memLoad.
     **/
    public void setMemLoad(double memLoad) {
	this.memLoad = memLoad;
    }

    /**
     * Get the value of memLoad.
     **/
    public double getMemLoad() {
	return memLoad;
    }
}
