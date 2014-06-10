package com.zenika.zenfoot.gae.model;

/**
 * Created by raphael on 04/06/14.
 */
public class Demande {

    protected String email;

    protected Long id;


    public Demande() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
