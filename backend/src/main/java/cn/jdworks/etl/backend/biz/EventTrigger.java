package cn.jdworks.etl.backend.biz;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;

import cn.jdworks.etl.backend.bean.TriggerTask;

public class EventTrigger {
	
	@Inject
	protected Dao dao;
	
	private List<TriggerTask> list;
	
	public List<TriggerTask> getAllTasks() {
		list = dao.query(TriggerTask.class, null);
		return list;
	}
}
