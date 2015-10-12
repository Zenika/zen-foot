package com.zenika.zenfoot.gae.dto;


public class LightLigueDTO {

    Long id;
    String name;
    GamblerDTO owner;

    public String getName() {
        return name;
    }

    public LightLigueDTO setName(String name) {
        this.name = name;
        return this;
    }


    public GamblerDTO getOwner() {
        return owner;
    }

    public void setOwner(GamblerDTO owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof LightLigueDTO)){
            return false;
        }
        return ((LightLigueDTO)obj).getName().equals(this.getName());
    }
    
}
