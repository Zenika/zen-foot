package com.zenika.zenfoot.model;

import java.io.Serializable;

public class Team implements Serializable, Comparable<Team> {
    private String name;
    private String imageName;

    public Team(String name, String imageName) {
        assert name != null && !name.trim().isEmpty() : "new Team: name must not be null";
        assert imageName != null && !imageName.trim().isEmpty() : "new Team: imageName must not be null";

        this.name = name;
        this.imageName = imageName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Team other = (Team) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team: " + name;
    }

    public int compareTo(Team otherTeam) {
        return name.compareToIgnoreCase(otherTeam.name);
    }
}