/**
 * 
 */
package com.zenika.zenfoot.gae.module.service;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.Sport;

/**
 * @author vickrame
 *
 */
public class SportService extends AbstractGenericService<SportService, Sport>{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SportService(Class<SportService> type,GenericDAO<Sport> dao) {
		super(type, dao);
	}
    
}
