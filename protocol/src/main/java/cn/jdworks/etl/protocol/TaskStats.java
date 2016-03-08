package cn.jdworks.etl.protocol;

import java.util.Hashtable;
import java.io.*;

public class TaskStats implements Serializable {
	private int id;
	private int threadNum;
	private Hashtable<String, Integer> dbConns = new Hashtable<String, Integer>();

	public TaskStats() {
		super();
	}

	public TaskStats(int id, int threadNum, Hashtable<String, Integer> dbConns) {
		super();
		this.id = id;
		this.threadNum = threadNum;
		this.dbConns = dbConns;
	}

	public static TaskStats fromBytes(byte[] bytes) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			TaskStats stats = (TaskStats) ois.readObject();
			bais.close();
			ois.close();
			return stats;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public byte[] toBytes() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			byte[] bytes = baos.toByteArray();
			baos.close();
			oos.close();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
	 * Set the value of threadNum.
	 **/
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	/**
	 * Get the value of threadNum.
	 **/
	public int getThreadNum() {
		return threadNum;
	}

	/**
	 * Set the value of dbConns.
	 **/
	public void setDbConns(Hashtable<String, Integer> dbConns) {
		this.dbConns = dbConns;
	}

	/**
	 * Get the value of dbConns.
	 **/
	public Hashtable<String, Integer> getDbConns() {
		return dbConns;
	}

}
