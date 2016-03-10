package cn.jdworks.etl.executor;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import cn.jdworks.etl.protocol.TaskStats;

public class StatsReceiver{
	
	private TaskStats stats;
	private int port = 5050;

	public TaskStats recive() {
		try {
			DatagramSocket socket = new DatagramSocket(port,
					InetAddress.getByName("127.0.0.1"));
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			byte data[] = packet.getData();// 接收的数据
			stats = TaskStats.fromBytes(data);
			System.out.println("***Recive State***");
			System.out.println("TaskId: " + stats.getId());
			System.out.println("TheadNum: " + stats.getThreadNum());
			socket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stats;
	}
	
	public TaskStats getStats() {
		return stats;
	}

	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				StatsReceiver receiver = new StatsReceiver();
				while(true) {
					receiver.recive();
				}
			}
		}.start();

	}
}