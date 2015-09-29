package com.zenika.zenfoot.gae.services;

import com.google.appengine.labs.repackaged.com.google.common.collect.Sets;
import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.dto.BetDTO;
import com.zenika.zenfoot.gae.dto.GamblerDTO;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.gae.utils.CalculateScores;
import com.zenika.zenfoot.gae.utils.KeyBuilder;
import com.zenika.zenfoot.gae.model.User;
import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerService extends AbstractGenericService<Gambler> {

    final private MatchService matchService;
    final private BetService betService;
    final private TeamService teamService;
    final private MapperFacadeFactory mapper;

    public GamblerService(MatchService matchService, TeamService teamService,
            MapperFacadeFactory mapper, BetService betService, GamblerDAO gamblerDao) {
        super(gamblerDao);
        this.matchService = matchService;
        this.teamService = teamService;
        this.betService = betService;
        this.mapper = mapper;
    }
    
    public List<Bet> getBets(Gambler gambler) {
        return ((GamblerDAO) this.getDao()).getBets(gambler);
    }

    public Gambler getGamblerFromEmailAndEvent(String email, Event event) {
        return ((GamblerDAO) this.getDao()).getGamblerFromEmailAndEvent(email, event);
    }

    //TODO : remove once mocked users are removed
    public Key<Gambler> createGambler(User user, Event event) {
        GamblerDTO gambler = new GamblerDTO(user.getEmail());
        gambler.setPrenom(user.getPrenom());
        gambler.setNom(user.getNom());
        gambler.setEvent(event);

        return this.createOrUpdate(mapper.getMapper().map(gambler, Gambler.class));
    }
    
    public Gambler createOrUpdateAndReturn(User user, Event event) {
        GamblerDTO gambler = new GamblerDTO(user.getEmail());
        gambler.setPrenom(user.getPrenom());
        gambler.setNom(user.getNom());
        gambler.setEvent(event);

        return this.createOrUpdateAndReturn(mapper.getMapper().map(gambler, Gambler.class));
    }

    public Bet getBetByGamblerAndMatchId(Gambler gambler, Long matchId) {
        return this.betService.getBetByGamblerAndMatchId(gambler, matchId);
    }
    
    public void updateBets(List<BetDTO> newBets, Gambler gambler, Event event) {
        DateTime registerTime = DateTime.now();
        Key<Gambler> keyGambler = KeyBuilder.buildGamblerKey(gambler.getId(), event.getId());
        for (BetDTO bet : newBets) {
            if (bet.getScore1() != null && bet.getScore2() != null) {
                Bet existingBet = getBetByGamblerAndMatchId(gambler, bet.getMatchId());
                Match correspondingMatch = matchService.getMatch(bet.getMatchId(), event);

                //Check the bet already existed in the database
                if(correspondingMatch.getDate().isAfter(registerTime)){
                    if (existingBet == null) {
                        existingBet = new Bet();
                        existingBet.setMatchId(bet.getMatchId());
                    }
                    existingBet.setGambler(keyGambler);
                    existingBet.setScore1(bet.getScore1());
                    existingBet.setScore2(bet.getScore2());
                    betService.createOrUpdate(existingBet);
                }
            }
        }
    }

    private void calculateScores(Match match, boolean add) {
        List<Bet> bets = betService.getBetsByMatchId(match.getId());
        if(bets != null && bets.size() > 0) {
            for (Bet bet : bets) {
                if (bet !=null && bet.wasMade()) {
                    Gambler gamblerRanking = this.getFromKey(bet.getGambler());

                    int points = CalculateScores.calculateScores(bet,match);
                    if(points>0){
                        if(add){
                            gamblerRanking.addPoints(points);
                        }
                        else{
                            gamblerRanking.removePoints(points);
                        }
                    }
                    this.createOrUpdate(gamblerRanking);
                }
            }
        }
    }

    public void setScore(Match matchFormerValue, Match matchNewValue) {
        //Remove points which were added thanks to that match if the match already had a result (that's if a result had been
        // given by mistake to the match, and the admin would like to change it. Scores have to be calculated again).
        if(matchFormerValue.isScoreUpdated()){
            this.calculateScores(matchFormerValue,false);
        }
        //registering the new result
        // We re-register the former value of the match with the new score values only, to make sure that nothing
        // else is updated in the match. We also set the updated property to true
        matchFormerValue.setScoreUpdated(true);
        matchFormerValue.setScore1(matchNewValue.getScore1());
        matchFormerValue.setScore2(matchNewValue.getScore2());
        matchService.createOrUpdate(matchFormerValue);
        //calculate scores again with the new match result
        this.calculateScores(matchNewValue,true);
    }

    /**
     * Get all the
     *
     * @param gambler
     * @return
     */
    public Set<Gambler> wantToJoin(Gambler gambler) {
        return null;
    }

    public Set<Gambler> wantToJoin(Long id){
        Ligue team = teamService.getFromID(id);
        HashSet<Gambler> gamblers = Sets.newHashSet(this.wantToJoin(team.getName()));
        return gamblers;
    }

    public List<Gambler> wantToJoin(String name) {
        return ((GamblerDAO)this.getDao()).gamblersWannaJoin(name);
    }
    
    public int nbGamblersInTeam(Ligue team){
        return ((GamblerDAO)this.getDao()).nbGamblersInTeam(team);
    }


}
