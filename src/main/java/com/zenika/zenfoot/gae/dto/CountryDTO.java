package com.zenika.zenfoot.gae.dto;

/**
 * Created by mathias on 06/06/16.
 */
public class CountryDTO {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    private Long id;

    private String code;

    private String displayName;

    public CountryDTO() {}
}
