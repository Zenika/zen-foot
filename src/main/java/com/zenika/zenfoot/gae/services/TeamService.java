package com.zenika.zenfoot.gae.services;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.model.Ligue;


/**
 * Created by raphael on 03/06/14.
 */
public class TeamService extends AbstractGenericService<Ligue, Long> {

    public TeamService(TeamDAO teamDAO) {
        super(teamDAO);
    }
    
    public Optional<Ligue> get(String name) {
        return ((TeamDAO)this.getDao()).get(name);
    }
}
