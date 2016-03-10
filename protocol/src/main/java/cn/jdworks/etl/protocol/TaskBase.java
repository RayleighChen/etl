package cn.jdworks.etl.protocol;

import java.net.*;
import cn.jdworks.etl.protocol.TaskStats;

public class TaskBase {

	private TaskStats stats;
	private int port = 5050;

	public TaskBase(String[] args) {
		this.stats = new TaskStats();
		try {
			int id = Integer.parseInt(args[0]);
			this.stats.setId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			DatagramSocket socket = new DatagramSocket();
			byte[] buffer = this.stats.toBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					addr, port);
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onThreadCreated() {
		this.stats.setThreadNum(this.stats.getThreadNum() + 1);
	}

	public void onThreadReleased() {
		this.stats.setThreadNum(this.stats.getThreadNum() - 1);
	}

	public void onConnectionCreated(String dbName) {
		Integer num = this.stats.getDbConns().get(dbName);
		if (num == null) {
			num = 1;
		} else {
			num++;
		}
		this.stats.getDbConns().put(dbName, num);
	}

	public void onConnectionReleased(String dbName) {
		Integer num = this.stats.getDbConns().get(dbName);
		if (num != null) {
			num--;
			if (num > 0) {
				this.stats.getDbConns().put(dbName, num);
			} else {
				this.stats.getDbConns().remove(dbName);
			}
		}
	}
}
