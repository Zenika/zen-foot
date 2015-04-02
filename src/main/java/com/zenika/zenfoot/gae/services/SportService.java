/**
 * 
 */
package com.zenika.zenfoot.gae.services;

import restx.factory.Component;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dao.SportDAO;
import com.zenika.zenfoot.gae.model.Sport;

/**
 * 
 * @author vickrame
 *
 */
@Component
public class SportService extends AbstractGenericService<SportService, Sport> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SportDAO sportDAO;

	public SportService(SportDAO sportDAO) {
		super(SportService.class, sportDAO);
		this.sportDAO = sportDAO;
	}
}
