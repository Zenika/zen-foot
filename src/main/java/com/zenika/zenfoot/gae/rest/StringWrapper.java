package com.zenika.zenfoot.gae.rest;

/**
 * Created by raphael on 19/08/14.
 */
public class StringWrapper {

    private String string;

    public StringWrapper(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
