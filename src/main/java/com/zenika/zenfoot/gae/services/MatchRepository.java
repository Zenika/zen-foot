package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Participant;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by raphael on 28/04/14.
 */
public class MatchRepository {

    protected HashMap<Long,Match> matchs;

    public MatchRepository(){
        matchs = new HashMap<>();

        Participant participant1 = new Participant().setPays("France");
        Participant participant2 = new Participant().setPays("Bresil");
        Match match1 = new Match(new DateTime(2014,6,22,22,0),participant1,participant2);

        Participant participant3 = new Participant().setPays("Coree du Sud");
        Participant participant4 = new Participant().setPays("Moldavie");
        Match match2 = new Match(new DateTime(2014,6,24,21,0),participant3,participant4);

        this.createMatch(match1);
        this.createMatch(match2);

    }


    /*
    CRUD methods
     */

    public void createMatch(Match match){
        this.matchs.put(match.getId(),match);
    }

    public void delete (Long id){
        this.matchs.remove(id);
    }

    public Match getMatch(Long id){
        return this.matchs.get(id);
    }

    public List<Match> getAll() {
        return new ArrayList<>(matchs.values());
    }
}
