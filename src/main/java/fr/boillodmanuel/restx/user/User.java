package fr.boillodmanuel.restx.user;

import java.util.Collection;

import org.joda.time.DateTime;
import restx.security.RestxPrincipal;

import com.google.common.collect.ImmutableSet;

public class User implements RestxPrincipal {

	private String email;

	private String name;

	private Collection<String> roles;

    private String passwordHash;

    private DateTime lastUpdated;

	public User() {

	}

	@Override
	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
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

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public DateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(DateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
	public ImmutableSet<String> getPrincipalRoles() {
		return ImmutableSet.copyOf(this.roles);
	}

}
