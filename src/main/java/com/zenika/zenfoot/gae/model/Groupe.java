package com.zenika.zenfoot.gae.model;

/**
 * Created by raphael on 28/04/14.
 */
public enum Groupe {

    A("A"),B("B"),C("C"),D("D"),E("E"),F("F"), G("G"), H("H");

    protected String groupe;

    private Groupe(String groupe){
        this.groupe=groupe;
    }

    @Override
    public String toString() {
        return "groupe "+this.groupe;
    }
}
