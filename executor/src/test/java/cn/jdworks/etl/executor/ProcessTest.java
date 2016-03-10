package cn.jdworks.etl.executor;

import org.junit.*;
import java.lang.*;
import java.util.*;

public class ProcessTest {

	@Test
	public void Test() {
		try {
			List<String> list = new ArrayList<String>();
			ProcessBuilder pb = null;
			Process p = null;
			// list the files and directorys under C:\
			list.add("java");
			list.add("-jar");
			list.add("../footask/target/footask-tmp.jar");
			list.add("1234");
			list.add("3");
			pb = new ProcessBuilder(list);
			p = pb.start();

			// process error and output message
			StreamWatch errorWatch = new StreamWatch(p.getErrorStream(),
					"ERROR");
			StreamWatch outputWatch = new StreamWatch(p.getInputStream(),
					"OUTPUT");
			// start to watch
			errorWatch.start();
			outputWatch.start();
			// wait for exit
			int exitVal = p.waitFor();
			// print the content from ERROR and OUTPUT
			System.out.println("ERROR: " + errorWatch.getOutput());
			System.out.println("OUTPUT: " + outputWatch.getOutput());
			System.out.println("the return code is " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
