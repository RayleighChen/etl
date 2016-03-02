package cn.jdworks.etl.backend;

import java.util.Date;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import cn.jdworks.etl.backend.bean.*;

public class MainSetup implements Setup {


    public void init(NutConfig conf) {
        Ioc ioc = conf.getIoc();
        Dao dao = ioc.get(Dao.class);
        Daos.createTablesInPackage(dao, "cn.jdworks.etl.backend.bean", false);

	/*增加一些测试任务数据*/
        if (dao.count(TimerTask.class) == 0) {
	    for(int i=1;i<10;i++){
		TimerTask task = new TimerTask();
		task.setName("测试定时任务"+i);
		task.setScript("task"+i+".jar");
		task.setExeTime(new Date());
		task.setExeInterval(100000);
		task.setDescription("这是任务描述");
		task.setStatus(TaskStatusType.ENABLED.value());
		dao.insert(task);
	    }
        }
    }

    public void destroy(NutConfig conf) {
    }

}
