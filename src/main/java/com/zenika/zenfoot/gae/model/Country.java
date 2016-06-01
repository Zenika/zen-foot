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
public class Country implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String name;

    private String displayName;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

//	/**
//	 * @param id the id to set
//	 */
//	public void setIdPays(Long id) {
//		this.id = id;
//	}

    /**
     * @return the name
     */
    public String getCountryName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param displayName the display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Country() {
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Country [id=").append(id).append(", name=")
                .append(name).append(", displayName=").append(displayName).append("]");
        return builder.toString();
    }

    public Country(Long id, String name, String displayName) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
    }

}
