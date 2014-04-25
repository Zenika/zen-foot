package com.zenika.zenfoot.gae.model;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Pays {

    @Id
	private String name;
	
	public Pays(){
		
	}

	public String getName() {
		return name;
	}

	public Pays setName(String name) {
		this.name = name;
		return this;
	}

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Pays))return false;
        return ((Pays)obj).getName().equals(this.getName());
    }
}
