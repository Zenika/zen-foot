package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by raphael on 28/04/14.
 */
@Entity
public class Participant {

    @Id
    protected String pays;

    public Participant() {
    }

    public String getPays() {
        return pays;
    }

    public Participant setPays(String pays) {
        this.pays = pays;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Participant)) return false;
        return ((Participant)obj).getPays().equals(this.getPays());
    }
}
