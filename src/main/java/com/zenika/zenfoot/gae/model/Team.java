package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class Team {

    public Long id;

    public String name;

    public Team() {

    }

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
