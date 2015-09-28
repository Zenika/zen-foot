package com.zenika.zenfoot.gae.model;

import com.google.common.collect.ImmutableSet;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.zenika.zenfoot.gae.utils.PasswordUtils;
import restx.security.RestxPrincipal;

import java.util.Collection;

@Entity
public class User implements RestxPrincipal {

    @Id
	private String email;

	private String name;

    private String prenom;

	private Collection<String> roles;

    private String passwordHash;

    private Boolean isActive;
    
	public User() {

	}

	@Override
	public String getName() {
		return email;
	}

    public String getNom(){
        return this.name;
    }

	public User setName(String name) {
		this.name = name;
		return this;
	}

    public String getPrenom() {
        return prenom;
    }

    public User setPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public String getId(){
        return this.email;
    }

    public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public User setRoles(Collection<String> roles) {
		this.roles = roles;
		return this;
	}

    public Collection<String> getRoles() {
        return roles;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String password) {
        setPasswordHash(PasswordUtils.getPasswordHash(password));
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /*
    @Transient
    public DateTime getLastUpdated() {
        return lastUpdated;
    }


    public void setLastUpdated(DateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    */

    @Override
	public ImmutableSet<String> getPrincipalRoles() {
		return ImmutableSet.copyOf(this.roles);
	}

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof User))return false;
        return ((User)obj).getEmail().equals(this.getEmail());
    }
    
    public Boolean getIsActive() {
		return isActive;
	}
    
    public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
