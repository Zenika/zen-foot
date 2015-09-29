/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.dao;

import com.googlecode.objectify.ObjectifyService;
import com.zenika.zenfoot.gae.GenericDAO;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Ligue;
import java.util.List;

/**
 *
 * @author nebulis
 */
public class LigueDAOImpl extends GenericDAO<Ligue> implements LigueDAO {

    @Override
    public List<Ligue> getLiguesFromEvent(Event parent) {
        return ObjectifyService.ofy().load().type(Ligue.class).ancestor(parent).list();
    }

    @Override
    public List<Ligue> getLiguesWithMembersFromEvent(Event parent) {
        return ObjectifyService.ofy().load().group(Ligue.Members.class).type(Ligue.class).ancestor(parent).list();
    }
}
