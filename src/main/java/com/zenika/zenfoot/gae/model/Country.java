/**
 * 
 */
package com.zenika.zenfoot.gae.model;

import java.io.Serializable;
import java.util.Locale;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.neovisionaries.i18n.CountryCode;

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

    private String code;

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
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return The display code
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param displayName the display code
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
        builder.append("Country [id=").append(id).append(", code=")
                .append(code).append(", displayName=").append(displayName).append("]");
        return builder.toString();
    }

    public Country(Long id, String displayName, String countryCode) {
        this.id = id;
        this.code = countryCode;
        this.displayName = displayName;
    }

    public Country(CountryCode code) {
        this.id = (long)code.getNumeric();
        this.displayName = code.toLocale().getDisplayCountry(Locale.FRENCH);
        this.code = code.getAlpha2().toLowerCase();
    }
}
