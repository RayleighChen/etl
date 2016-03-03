package cn.jdworks.etl.backend.module;

import cn.jdworks.etl.backend.bean.TimerTaskExe;

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

	@At("/get/?")
	@GET
	public TimerTaskExe getTaskExe(int Id) {
		TimerTaskExe t = dao.fetch(TimerTaskExe.class, Id);
		return t;
	}

	@At("/?")
	@POST
	public boolean updateTaskExe(int Id, @Param("..") TimerTaskExe task) {
		// TODO 这里是实现代码
//		 NutMap re = new NutMap();
//	        String msg = checkUser(user, false);
//	        if (msg != null){
//	            return re.setv("ok", false).setv("msg", msg);
//	        }
//	        user.setName(null);// 不允许更新用户名
//	        user.setCreateTime(null);//也不允许更新创建时间
//	        user.setUpdateTime(new Date());// 设置正确的更新时间
//	        dao.updateIgnoreNull(user);// 真正更新的其实只有password和salt
//	        return re.setv("ok", true);
		return true;
	}
	
	@At("/?")
	@POST
	public boolean addTaskExe(int Id, @Param("..") TimerTaskExe task) {
		// TODO 这里是实现代码
//		 NutMap re = new NutMap();
//	        String msg = checkUser(user, false);
//	        if (msg != null){
//	            return re.setv("ok", false).setv("msg", msg);
//	        }
//	        user.setName(null);// 不允许更新用户名
//	        user.setCreateTime(null);//也不允许更新创建时间
//	        user.setUpdateTime(new Date());// 设置正确的更新时间
//	        dao.updateIgnoreNull(user);// 真正更新的其实只有password和salt
//	        return re.setv("ok", true);
		return true;
	}
	
	@At("/?")
	@DELETE
	public void deleteTaskExe(int Id) {
		// TODO 这里是实现代码
		
	}
	
//    @At
//    public Object delete(@Param("id")int id, @Attr("me")int me) {
//        if (me == id) {
//            return new NutMap().setv("ok", false).setv("msg", "不能删除当前用户!!");
//        }
//        dao.delete(User.class, id); // 再严谨一些的话,需要判断是否为>0
//        return new NutMap().setv("ok", true);
//    }
//    
	// default to @At("/TimerTaskExe/count")
	@At
	public int count() {
		executorManager.foo();
		return dao.count(TimerTaskExe.class);
	}
}
