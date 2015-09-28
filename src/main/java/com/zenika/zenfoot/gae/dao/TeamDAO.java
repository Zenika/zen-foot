package com.zenika.zenfoot.gae.dao;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.Ligue;


public interface TeamDAO  extends IGenericDAO<Ligue>  {

    Optional<Ligue> get(String name);
    
}
