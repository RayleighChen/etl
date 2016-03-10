package cn.jdworks.etl.backend.module;

import cn.jdworks.etl.backend.bean.TimerTask;

import org.nutz.dao.Dao;


import org.apache.activemq.thread.Task;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.*;

import java.util.Date;
import java.util.List;
import cn.jdworks.etl.backend.biz.ExecutorManager;

@IocBean
@At("/timertask")
@Ok("json")
@Fail("http:500")
public class TimerTaskModule {

	@Inject
	protected Dao dao;

	@Inject
	protected ExecutorManager executorManager;

	@At("/")
	@GET
	public List<TimerTask> getAllTasks() {
		List<TimerTask> list = dao.query(TimerTask.class, null);
		return list;
	}

	protected String checkUser(Task task, boolean create) {
		if (task == null) {
			return "空对象";
		}
		return null;

	}

	@At("/?")
	@GET
	public TimerTask getTask(int Id) {
		TimerTask t = dao.fetch(TimerTask.class, Id);
		return t;
	}

	@At("/add")
	@POST
	public boolean add(@Param("..") TimerTask task) {

		task.setDescription(task.getDescription());
		task.setName(task.getName());
		task.setExeTime(new Date());
		task.setScript(task.getScript());
		task.setStatus(task.getStatus());
		task = dao.insert(task);
		return true;
	}

	@At("/update")
	@POST
	public boolean updateTask(@Param("..") TimerTask task) {
		System.out.println(task.getId());
		task.setDescription(task.getDescription());
		task.setScript(task.getScript());
		task.setStatus(task.getStatus());
		task.setExeTime(new Date());
		task.setName(task.getName());
		dao.update(task);
		return true;
	}

	@At("/?")
	@DELETE
	public boolean deleteTask(@Param("Id") int Id) {

		if (dao.fetch(TimerTask.class, Cnd.where("Id", "=", Id)) != null) {
//			dao.delete(TimerTask.class, Id);
			TimerTask t = dao.fetch(TimerTask.class, Id);
			t.setStatus(2);
			dao.update(t);
			return true;
		} else {
			return false;
		}
	}

	// default to @At("/timertask/count")
	@At
	public int count() {
		return dao.count(TimerTask.class);
	}
}
