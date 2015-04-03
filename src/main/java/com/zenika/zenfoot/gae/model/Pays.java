/**
 * 
 */
package com.zenika.zenfoot.gae.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author vickrame
 *
 */
@Entity
public class Pays implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPays;
	
	private String nomPays;

	/**
	 * @return the idPays
	 */
	public Long getIdPays() {
		return idPays;
	}

	/**
	 * @param idPays the idPays to set
	 */
	public void setIdPays(Long idPays) {
		this.idPays = idPays;
	}

	/**
	 * @return the nomPays
	 */
	public String getNomPays() {
		return nomPays;
	}

	/**
	 * @param nomPays the nomPays to set
	 */
	public void setNomPays(String nomPays) {
		this.nomPays = nomPays;
	}

	public Pays() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pays [idPays=").append(idPays).append(", nomPays=")
				.append(nomPays).append("]");
		return builder.toString();
	}

	public Pays(Long idPays, String nomPays) {
		this.idPays = idPays;
		this.nomPays = nomPays;
	}
	
	
}
