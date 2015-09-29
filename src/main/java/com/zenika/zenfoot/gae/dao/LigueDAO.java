/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.gae.IGenericDAO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Ligue;
import java.util.List;

/**
 *
 * @author nebulis
 */
public interface LigueDAO extends IGenericDAO<Ligue> {

    List<Ligue> getLiguesFromEvent(Event event);

    List<Ligue> getLiguesWithMembersFromEvent(Event event);
    
}
