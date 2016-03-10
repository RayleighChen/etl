package cn.jdworks.etl.protocol;

import java.io.Serializable;
import java.util.UUID;

public class Guid implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4823574155884869284L;
	private String id;
	
	public Guid() {
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}
}
