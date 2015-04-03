/**
 * 
 */
package com.zenika.zenfoot.gae.model;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author vickrame
 *
 */
@Entity
public class Sport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;

	public Sport(){
		
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public Sport(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
