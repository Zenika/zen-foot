package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.dao.PaysDAO;
import com.zenika.zenfoot.gae.model.Pays;

public class PaysService2 {

	
	private PaysDAO paysDAO;
	
	public PaysService2(PaysDAO paysDAO){
		this.paysDAO = paysDAO;
	}
	
    public void createUpdate(Pays pays) {
        this.paysDAO.createUpdate(pays);
    }
}
