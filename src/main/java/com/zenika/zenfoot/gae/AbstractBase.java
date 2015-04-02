package com.zenika.zenfoot.gae;

import java.io.Serializable;
import java.util.logging.Logger;

public abstract class AbstractBase<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Class<T> type;
	
	
	protected Logger logger = null;
	
	
	
	public AbstractBase(Class<T> type){
		this.type = type;
		logger = Logger.getLogger(type.getName());
		}


	/**
	 * @return the type
	 */
	public Class<T> getType() {
		return type;
	}

	
}
