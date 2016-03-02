package cn.jdworks.etl.backend.module;

import cn.jdworks.etl.backend.bean.TimerTask;

import org.nutz.dao.Dao;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.*;
import org.nutz.json.*;
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
    public List<TimerTask> getAllTasks()
    {
	List<TimerTask> list = dao.query(TimerTask.class,null);
	return list;
    }
    
    @At("/?")
    @GET
    public TimerTask getTask(int Id){
	TimerTask t = dao.fetch(TimerTask.class,Id);
	return t;
    }

    @At("/?")
    @POST
    public boolean updateTask(int Id, @Param("..") TimerTask task){
	// TODO 这里是实现代码
	return true;
    }

    @At("/?")
    @DELETE
    public void deleteTask(int Id){
	// TODO 这里是实现代码
    }

    //default to @At("/timertask/count")
    @At
    public int count() {
	executorManager.foo();
        return dao.count(TimerTask.class);
    }
}

