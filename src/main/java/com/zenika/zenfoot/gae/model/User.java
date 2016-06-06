package com.zenika.zenfoot.gae.model;

import com.google.common.collect.ImmutableSet;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.utils.PasswordUtils;
import restx.security.RestxPrincipal;

import java.util.Collection;
import java.util.HashSet;

@Entity
public class User implements RestxPrincipal {

    @Id
	private String email;

    @Index
	private String lastName;

    private String firstName;

	private Collection<String> roles;

    private String passwordHash;

    private Boolean isActive;

    private Integer activationToken;

    //all is deprecated: use lastName and firstName
    @Deprecated
    private String name;

    @Deprecated
    private String prenom;
    
	public User() {
        roles = new HashSet<>();
	}

    @Deprecated
    public String getPrenom() {
        return prenom;
    }

    @Deprecated
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Deprecated
    public void setName(String name) {
        this.name = name;
    }

    @Deprecated
    public String getName2() {
        return name;
    }

	@Override
	public String getName() {
		return email;
	}

    public String getLastName(){
        return this.lastName;
    }

	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
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

    public void hashAndSetPassword(String password) {
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

    public Integer getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(Integer activationToken) {
        this.activationToken = activationToken;
    }

    public boolean isAdmin(){
        return this.getRoles().contains(Roles.ADMIN);
    }
}
