/**
 * 
 */
package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.Sport;

/**
 * @author vickrame
 *
 */
public class SportService extends AbstractGenericService<Sport>{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SportService(GenericDAO<Sport> dao) {
		super(dao);
	}
    
}
