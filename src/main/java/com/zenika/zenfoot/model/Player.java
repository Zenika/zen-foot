package com.zenika.zenfoot.model;

import java.io.Serializable;

public class Player extends AbstractModel implements Serializable {

    private String email;
    private String password;
    private int points = 0;
    private boolean pending = true;
    private boolean admin = false;

    public Player() {
    }

    public Player(String email) {
        assert email != null && !email.trim().isEmpty() : "new User: email must not be null";
        this.email = email;
    }

    public Player(String email, String password) {
        this(email);
        assert password != null && !password.trim().isEmpty() : "new User: password must not be null";
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getAlias() {
        return email.split("@")[0];
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + email;
    }
}