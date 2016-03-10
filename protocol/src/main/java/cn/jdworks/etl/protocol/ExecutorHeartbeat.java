package cn.jdworks.etl.protocol;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;

public class ExecutorHeartbeat implements Serializable {

	private static final long serialVersionUID = 2566856946706200757L;

	private Guid id;
	private String ipAddr;
	private double cpuLoad;
	private double memLoad;
	private Hashtable<Integer, TaskStats> tasks;

	public ExecutorHeartbeat() {
		super();
		id = new Guid();
		try {
			ipAddr = InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ExecutorHeartbeat(double cpuLoad, double memLoad,
			Hashtable<Integer, TaskStats> tasks) {
		super();
		id = new Guid();
		try {
			ipAddr = InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.tasks = tasks;
		this.cpuLoad = cpuLoad;
		this.memLoad = memLoad;
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

	public Hashtable<Integer, TaskStats> getTasks() {
		return tasks;
	}

	public void setTasks(Hashtable<Integer, TaskStats> tasks) {
		this.tasks = tasks;
	}

	public String getId() {
		return id.getId();
	}

	public String getIpAddr() {
		return ipAddr;
	}	
}
