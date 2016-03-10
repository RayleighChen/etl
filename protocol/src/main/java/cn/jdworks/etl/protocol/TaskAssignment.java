package cn.jdworks.etl.protocol;

import java.io.Serializable;

public class TaskAssignment implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2566856946706200758L;
	/**
	 * 
	 */
	private int id;
    private String scriptName;
    private String scriptArgs;
 
    public TaskAssignment() {
       super();
    }
 
    public TaskAssignment(int id, String scriptName, String scriptArgs) {
        super();
        this.id = id;
        this.scriptName = scriptName;
        this.scriptArgs = scriptArgs;
    }


    /**
     * Set the value of id.
     **/
    public void setId(int id) {
	this.id = id;
    }

    /**
     * Get the value of id.
     **/
    public int getId() {
	return id;
    }
    /**
     * Set the value of scriptName.
     **/
    public void setScriptName(String scriptName) {
	this.scriptName = scriptName;
    }

    /**
     * Get the value of scriptName.
     **/
    public String getScriptName() {
	return scriptName;
    }
    /**
     * Set the value of scriptArgs.
     **/
    public void setScriptArgs(String scriptArgs) {
	this.scriptArgs = scriptArgs;
    }

    /**
     * Get the value of scriptArgs.
     **/
    public String getScriptArgs() {
	return scriptArgs;
    }
}
