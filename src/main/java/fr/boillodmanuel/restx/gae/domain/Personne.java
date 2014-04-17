package fr.boillodmanuel.restx.gae.domain;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;

import com.googlecode.objectify.annotation.Id;

@Entity
public class Personne implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2145825218255889542L;

	@Id
	private Long id;

	private String personne = "";

	public Personne() {

	}
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getPersonne() {
		return personne;
	}

	public Personne setPersonne(String personne) {
		this.personne = personne;
		return this;
	};

}
