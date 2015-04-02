package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.Pays;

public class PaysDAO extends GenericDAO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaysDAO() {
		super(Pays.class);
		System.out.println("on passe par ici");
	}

	
}
