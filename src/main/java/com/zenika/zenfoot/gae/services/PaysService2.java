package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dao.PaysDAO;
import com.zenika.zenfoot.gae.model.Pays;

public class PaysService2 extends AbstractGenericService<PaysService2, Pays> {

	
	private PaysDAO paysDAO;
	
	public PaysService2(PaysDAO paysDAO){
		super(PaysService2.class,paysDAO);
		this.paysDAO = paysDAO;
	}
	
}
