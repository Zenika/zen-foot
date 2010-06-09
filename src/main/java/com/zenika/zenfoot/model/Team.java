package com.zenika.zenfoot.model;

import java.io.Serializable;

public class Team extends AbstractModel implements Serializable, Comparable<Team> {

    private String name;
    private String imageName;

    public Team() {
    }

    public Team(String name, String imageName) {
        assert name != null && !name.trim().isEmpty() : "new Team: name must not be null";
        assert imageName != null && !imageName.trim().isEmpty() : "new Team: imageName must not be null";

        this.name = name;
        this.imageName = imageName;
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
        return getClass().getSimpleName() + ": " + name;
    }

    @Override
    public int compareTo(Team otherTeam) {
        return name.compareToIgnoreCase(otherTeam.name);
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
        hash = 13 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
}
