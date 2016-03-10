package cn.jdworks.etl.task;

import cn.jdworks.etl.protocol.TaskBase;

public class FooTask extends TaskBase {

	public FooTask(String[] args) {
		super(args);
	}

	public void run() {
		System.out.println("foo task start.");
		onThreadCreated();
		update();
		
		onThreadCreated();
		update();
		onThreadCreated();
		update();
		
		System.err.println("error log");
//		onThreadReleased();
//		update();
		
		System.err.println("error log");
		onConnectionCreated("mysql");
		update();
	
		System.err.println("error log");
		System.out.println("foo task end.");
	}

	public static void main(String[] args) {
		// String[] tmeStrings = { "0", "110" };
		FooTask task = new FooTask(args);
		task.run();
	}
}
