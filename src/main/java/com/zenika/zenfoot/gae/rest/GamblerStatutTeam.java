package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.StatutTeam;

/**
 * Created by raphael on 08/07/14.
 */
public class GamblerStatutTeam {

    private StatutTeam statutTeam;

    private Gambler gambler;

    public GamblerStatutTeam() {
    }

    public StatutTeam getStatutTeam() {
        return statutTeam;
    }

    public void setStatutTeam(StatutTeam statutTeam) {
        this.statutTeam = statutTeam;
    }

    public Gambler getGambler() {
        return gambler;
    }

    public void setGambler(Gambler gambler) {
        this.gambler = gambler;
    }
}
