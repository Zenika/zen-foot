/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenika.zenfoot.gae.utils;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Ligue;
import com.zenika.zenfoot.gae.model.Match;

/**
 *
 * @author nebulis
 */
public class KeyBuilder {
    
    public static Key<Gambler> buildGamblerKey(Long idGambler, Long idEvent) {
        Key<Event> keyEvent = Key.create(Event.class, idEvent);
        return Key.create(keyEvent, Gambler.class, idGambler);
    }
    
    public static Key<Match> buildMatchKey(Long idMatch, Long idEvent) {
        Key<Event> eventKey = Key.create(Event.class, idEvent);
        return Key.create(eventKey, Match.class, idMatch);
    }
    
    public static Key<Ligue> buildLigueKey(Long LigueMatch, Long idEvent) {
        Key<Event> eventKey = Key.create(Event.class, idEvent);
        return Key.create(eventKey, Ligue.class, LigueMatch);
    }
    
}
