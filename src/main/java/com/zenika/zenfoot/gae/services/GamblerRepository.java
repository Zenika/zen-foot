package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.model.Gambler;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerRepository {


    private GamblerDAO gamblerDAO;

    public GamblerRepository(GamblerDAO gamblerDAO) {
        this.gamblerDAO = gamblerDAO;
    }



    public Gambler getGambler(Long id) {
        return gamblerDAO.getGambler(id);

    }
    public Gambler getGamblerFromEmail(String email){
        return gamblerDAO.getGamblerFromEmail(email);
    }

    public void saveGambler(Gambler gambler) {
        gamblerDAO.saveGambler(gambler);
    }
}
