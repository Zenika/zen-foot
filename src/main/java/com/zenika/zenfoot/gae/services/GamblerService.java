package com.zenika.zenfoot.gae.services;

import com.google.appengine.labs.repackaged.com.google.common.collect.Sets;
import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dao.GamblerDAO;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.dao.TeamRankingDAO;
import com.zenika.zenfoot.gae.dto.BetDTO;
import com.zenika.zenfoot.gae.dto.GamblerDTO;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.gae.utils.CalculateScores;
import com.zenika.zenfoot.gae.utils.KeyBuilder;
import com.zenika.zenfoot.user.User;
import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerService {

    private GamblerRepository gamblerRepository;
    private MatchService matchService;
    private BetService betService;
    private TeamDAO teamDAO;
    private TeamRankingDAO teamRankingDAO;
    private MapperFacadeFactory mapper;
    private GamblerDAO gamblerDao;

    public GamblerService(GamblerRepository gamblerRepository, MatchService matchService, TeamDAO teamDAO, 
            TeamRankingDAO teamRankingDAO, MapperFacadeFactory mapper, BetService betService, GamblerDAO gamblerDao) {
        this.matchService = matchService;
        this.gamblerRepository = gamblerRepository;
        this.teamDAO = teamDAO;
        this.teamRankingDAO = teamRankingDAO;
        this.betService = betService;
        this.mapper = mapper;
        this.gamblerDao = gamblerDao;
    }

    public List<Gambler> getAll() {
        return gamblerRepository.getAll();
    }

    public Gambler get(User user, Event event) {
        return getFromEmailAndEvent(user.getEmail(), event);
    }

    public Gambler getFromEmailAndEvent(String email, Event event) {
        return gamblerRepository.getFromEmailAndEvent(email, event);
    }

    public Gambler get(Long id){
        return gamblerRepository.getGambler(id);
    }

    /**
     * Used to create gambler, and also to create the GamblerRanking object
     * @param user
     * @param matchs
     * @return
     */
    public Key<Gambler> createGambler(User user) {
        return createGambler(user);
    }

    //TODO : remove once mocked users are removed
    public Key<Gambler> createGambler(User user, Event event) {
        GamblerDTO gambler = new GamblerDTO(user.getEmail());
        gambler.setPrenom(user.getPrenom());
        gambler.setNom(user.getNom());
        gambler.setEvent(event);

        Key<Gambler> toRet = gamblerRepository.saveGambler(mapper.getMapper().map(gambler, Gambler.class));

        return toRet;
    }

    public Gambler updateGambler(Gambler gambler) {
        Key<Gambler> key = gamblerRepository.saveGambler(gambler);
        return gamblerRepository.getGambler(key);
    }

    public Gambler getGambler(Key<Gambler> gamblerKey) {
        return gamblerRepository.getGambler(gamblerKey);
    }
    

    public Bet getBetByMatchId(Gambler gambler, Long matchId) {
        return this.betService.getBetByMatchId(gambler, matchId);
    }
    
    public void updateBets(List<BetDTO> newBets, Gambler gambler, Event event) {
        DateTime registerTime = DateTime.now();
        Key<Gambler> keyGambler = KeyBuilder.buildGamblerKey(gambler.getId(), event.getId());
        for (BetDTO bet : newBets) {
            if (bet.getScore1() != null && bet.getScore2() != null) {
                Bet existingBet = getBetByMatchId(gambler, bet.getMatchId());
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
                    betService.createUpdate(existingBet);
                }
            }
        }
    }

    private void calculateScores(Match match,boolean add) {
        List<Gambler> gamblers = gamblerRepository.getAll();
        for (Gambler gambler : gamblers) {
            Bet bet = null;//getBetByMatchId(gambler, match.getId());
            if (bet !=null && bet.wasMade()) {
                Gambler gamblerRanking = gamblerDao.get(gambler.getId());
                
                int points = CalculateScores.calculateScores(bet,match);
                if(points>0){
                    if(add){
                        gamblerRanking.addPoints(points);
                    }
                    else{
                        gamblerRanking.removePoints(points);
                    }
                }
                gamblerDao.createUpdate(gamblerRanking);
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
        matchService.createUpdate(matchFormerValue);
        //calculate scores again with the new match result
        this.calculateScores(matchNewValue,true);
    }

    /**
     * Adds a list of team to the user. If the team doesn't exist in the DB, it is created and registered in the database
     * with the given gambler as its owner. The teamRanking object is also created
     * @param teams
     * @param gambler
     * @return
     */
    public Key<Gambler> addTeams(List<Team> teams, Gambler gambler) {

        Set<StatutTeam> teamsToRegister = new HashSet<>();
        for (Team team : teams) {
            Optional<Team> DBTeamOptional = teamDAO.get(team.getName());

            Team teamToRegister;
            boolean owner = false;

            if (DBTeamOptional.isPresent()) { // Team has already been created
                teamToRegister = DBTeamOptional.get();
//                logger.log(Level.INFO, "Id for team : " + toRegister.getId());
            } else { //The team was created by the user and thus, the latter is its owner
//                logger.log(Level.INFO, "No team found");
                team.setOwnerEmail(gambler.getEmail());
                Gambler gamblerRanking = gamblerDao.get(gambler.getId());
                Key<Team> teamKey = teamDAO.createUpdate(team);
                teamToRegister = teamDAO.get(teamKey);
                TeamRanking teamRanking = new TeamRanking();
                teamRanking.setTeamId(teamToRegister.getId());
                teamRanking.setPoints(gamblerRanking.getPoints());
                teamRankingDAO.createUpdate(teamRanking);
                owner = true;
            }

            //Checking that the gambler has not already joined the team
            if(!gambler.hasTeam(team)){
                StatutTeam statutTeam = new StatutTeam().setTeam(teamToRegister).setAccepted(owner);
                teamsToRegister.add(statutTeam);
            }

        }
        gambler.addTeams(teamsToRegister);
        return gamblerRepository.saveGambler(gambler);

    }

    public void updateTeams(Set<StatutTeam> registeredTeams, Gambler gambler) {
        gamblerRepository.saveGambler(gambler);
    }

    /**
     * Get all the teams whose owner is gambler
     *
     * @param gambler
     * @return
     */
    private Set<Team> ownedBy(Gambler gambler) {
        Set<Team> set = new HashSet<>();
        for(StatutTeam statutTeam:gambler.getStatutTeams()){
            Team team = statutTeam.getTeam();
            if(team.getOwnerEmail().equals(gambler.getEmail())){
                set.add(team);
            }
        }
        return set;
    }

    /**
     * Get all the
     *
     * @param gambler
     * @return
     */
    public Set<Gambler> wantToJoin(Gambler gambler) {
        Set<Team> ownedTeams = ownedBy(gambler);
        Set<Gambler> joining = new HashSet<>();

        for (Team team : ownedTeams) {
            joining.addAll(gamblerRepository.wantToJoin(team.getName()));
        }

        return joining;
    }

    public Set<Gambler> wantToJoin(Long id){
        Team team = teamDAO.get(id);
        HashSet<Gambler> gamblers = Sets.newHashSet(gamblerRepository.wantToJoin(team.getName()));
        return gamblers;
    }



}
