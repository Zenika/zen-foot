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

    protected Groupe groupe;

    public Participant() {
    }

    public String getPays() {
        return pays;
    }

    public Participant setPays(String pays) {
        this.pays = pays;
        return this;
    }

    public Participant setGroupe(Groupe groupe) {
        this.groupe = groupe;
        return this;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Participant)) return false;
        return ((Participant)obj).getPays().equals(this.getPays());
    }
}
