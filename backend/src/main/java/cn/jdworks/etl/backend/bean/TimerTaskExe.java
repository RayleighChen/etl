package cn.jdworks.etl.backend.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.*;

@Table("TimerTaskExe")
public class TimerTaskExe {
    @Id
    private int Id;
    /**
     * Set the value of Id.
     **/
    public void setId(int Id) {
	this.Id = Id;
    }

    /**
     * Get the value of Id.
     **/
    public int getId() {
	return Id;
    }

    @Column
    private int Id_TimerTask;

    /**
     * Set the value of Id_TimerTask.
     **/
    public void setId_TimerTask(int Id_TimerTask) {
	this.Id_TimerTask = Id_TimerTask;
    }

    /**
     * Get the value of Id_TimerTask.
     **/
    public int getId_TimerTask() {
	return Id_TimerTask;
    }



    @Column
    private Date ExeTime;

    /**
     * Set the value of ExeTime.
     **/
    public void setExeTime(Date ExeTime) {
	this.ExeTime = ExeTime;
    }

    /**
     * Get the value of ExeTime.
     **/
    public Date getExeTime() {
	return ExeTime;
    }

    @Column
    private String Log;

    /**
     * Set the value of Log.
     **/
    public void setLog(String Log) {
	this.Log = Log;
    }

    /**
     * Get the value of Log.
     **/
    public String getLog() {
	return Log;
    }

    @Column
    private int Status;
    /**
     * Set the value of Status.
     **/
    public void setStatus(int Status) {
	this.Status = Status;
    }

    /**
     * Get the value of Status.
     **/
    public int getStatus() {
	return Status;
    }

}
