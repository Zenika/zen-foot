package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.dao.GamblerRankingDAO;
import com.zenika.zenfoot.gae.model.GamblerRanking;

import java.util.List;

/**
 * Created by raphael on 25/08/14.
 */
public class GamblerRankingService {

    private GamblerRankingDAO gamblerRankingDAO;

    public GamblerRankingService(GamblerRankingDAO gamblerRankingDAO) {
        this.gamblerRankingDAO = gamblerRankingDAO;
    }

    /**
     * Reinitialize points to 0 for every gambler
     */
    public void reinitializePoints(){
        List<GamblerRanking> gamblerRankings = gamblerRankingDAO.getAll();

        for(GamblerRanking gamblerRanking : gamblerRankings){
            gamblerRanking.setPoints(0);
            gamblerRankingDAO.createUpdate(gamblerRanking);
        }
    }

    public List<GamblerRanking> getAll(){
        return gamblerRankingDAO.getAll();
    }

}
