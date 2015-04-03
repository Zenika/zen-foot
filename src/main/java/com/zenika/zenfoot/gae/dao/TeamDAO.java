package com.zenika.zenfoot.gae.dao;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.Team;


public interface TeamDAO  extends IGenericDAO<Team>  {

    Optional<Team> get(String name);
    
}
