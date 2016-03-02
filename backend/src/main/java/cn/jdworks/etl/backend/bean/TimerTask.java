package cn.jdworks.etl.backend.bean;

import java.util.Date;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.*;

@Table("TimerTask")
public class TimerTask {

    @Many(target = TimerTaskExe.class, field = "Id_TimerTask")
    private List<TimerTaskExe> Exes;

    public List<TimerTaskExe> getExes() {
        return Exes;
    }

    public void setExes(List<TimerTaskExe> Exes) {
        this.Exes = Exes;
    }
    
    @Id
    private int Id;

    @Name
    @Column
    private String Name;

    @Column
    private String Script;

    @Column
    private Date ExeTime;

    @Column
    private int ExeInterval;

    @Column
    private String Description;

    @Column
    private int Status;

    public int getId() {
        return Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Set the value of ExeInterval.
     **/
    public void setExeInterval(int ExeInterval) {
	this.ExeInterval = ExeInterval;
    }

    /**
     * Get the value of ExeInterval.
     **/
    public int getExeInterval() {
	return ExeInterval;
    }

    /**
     * Set the value of Script.
     **/
    public void setScript(String Script) {
	this.Script = Script;
    }

    /**
     * Get the value of Script.
     **/
    public String getScript() {
	return Script;
    }

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
    /**
     * Set the value of Description.
     **/
    public void setDescription(String Description) {
	this.Description = Description;
    }

    /**
     * Get the value of Description.
     **/
    public String getDescription() {
	return Description;
    }

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
