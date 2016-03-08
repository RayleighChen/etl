package cn.jdworks.etl.backend.module;

import cn.jdworks.etl.backend.bean.TimerTaskExe;

import org.nutz.dao.Dao;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.*;
import org.nutz.json.*;

import java.util.Date;
import java.util.List;
import cn.jdworks.etl.backend.biz.ExecutorManager;

@IocBean
@At("/timertaskexe")
@Ok("json")
@Fail("http:500")
public class TimerTaskExeModule {

	@Inject
	protected Dao dao;

	@Inject
	protected ExecutorManager executorManager;

	@At("/")
	@GET
	public List<TimerTaskExe> getAllTaskExes() {
		List<TimerTaskExe> list = dao.query(TimerTaskExe.class, null);
		return list;
	}

	@At("/?")
	@GET
	public TimerTaskExe getTaskExe(int Id) {
		TimerTaskExe t = dao.fetch(TimerTaskExe.class, Id);
		return t;
	}

	@At("/update")
	@POST
	public boolean updateTaskExe(@Param("..") TimerTaskExe task) {
		// TODO 这里是实现代码
		//System.out.println(task.getId());
		task.setExeTime(new Date());
		task.setStatus(task.getStatus());
		task.setLog(task.getLog());
		task.setId_TimerTask(task.getId_TimerTask());
		dao.update(task);
		return true;			
}
	
	@At("/add")
	@POST
	public boolean addTaskExe(@Param("..") TimerTaskExe task) {
		// TODO 这里是实现代码
		task.setExeTime(new Date());
		task.setStatus(task.getStatus());
		task.setLog(task.getLog());
		task.setId_TimerTask(task.getId_TimerTask());
		dao.insert(task);
		return true;
		
	}
	
	@At("/?")
	//@DELETE
	public boolean deleteTaskExe(@Param("Id")int Id) {
		
		if (dao.fetch(TimerTaskExe.class, Cnd.where("Id","=",Id)) != null) {
			dao.delete(TimerTaskExe.class,Id);
			return true;
		} else {
			return false;
		}
		
	}

	@At
	public int count() {
		executorManager.foo();
		return dao.count(TimerTaskExe.class);
	}
}
