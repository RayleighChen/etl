package cn.jdworks.etl.backend.bean;

import java.util.Date;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.*;

@Table("TriggerTask")
public class TriggerTask {

    @Many(target = TriggerTaskExe.class, field = "Id_TriggerTask")
    private List<TriggerTaskExe> Exes;

    public List<TriggerTaskExe> getExes() {
        return Exes;
    }

    public void setExes(List<TriggerTaskExe> Exes) {
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
    private String Url;

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
