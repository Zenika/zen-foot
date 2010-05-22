package com.zenika.zenfoot.model;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String password;
    private int points = 0;

    public User(String email) {
        assert email != null && !email.trim().isEmpty() : "new User: email must not be null";

        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.email != null ? this.email.hashCode() : 0);
        return hash;
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
        return "User: " + email;
    }
}
