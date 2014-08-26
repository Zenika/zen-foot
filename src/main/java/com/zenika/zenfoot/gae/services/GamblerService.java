package com.zenika.zenfoot.gae.services;

import com.google.appengine.labs.repackaged.com.google.common.collect.Sets;
import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dao.RankingDAO;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.dao.TeamRankingDAO;
import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.gae.utils.CalculateScores;
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
    private TeamDAO teamDAO;
    private RankingDAO rankingDao;
    private TeamRankingDAO teamRankingDAO;

    public GamblerService(GamblerRepository gamblerRepository, MatchService matchService, TeamDAO teamDAO, RankingDAO rankingDAO, TeamRankingDAO teamRankingDAO) {
        this.matchService = matchService;
        this.gamblerRepository = gamblerRepository;
        this.teamDAO = teamDAO;
        this.rankingDao=rankingDAO;
        this.teamRankingDAO = teamRankingDAO;
    }

    public List<Gambler> getAll() {
        return gamblerRepository.getAll();
    }

    public Gambler get(User user) {
        return getFromEmail(user.getEmail());
    }

    public Gambler getFromEmail(String email) {
        return gamblerRepository.getGamblerFromEmail(email);
    }

    public Gambler get(Long id){
        return gamblerRepository.getGambler(id);
    }

    public Bet getBetByMatchId(Gambler gambler, Long matchId) {
        for (Bet bet : gambler.getBets()) {
            if (bet.getMatchId().equals(matchId)) {
                return bet;
            }
        }
        return null;
    }

    public void updateBets(List<Bet> newBets, Gambler gambler) {
        DateTime registerTime = DateTime.now();
        for (Bet bet : newBets) {
            Bet existingBet = getBetByMatchId(gambler, bet.getMatchId());
            Match correspondingMatch = matchService.getMatch(bet.getMatchId());

            //Check the bet already existed in the database
            if(correspondingMatch.getDate().isAfter(registerTime)){
                if (existingBet == null) {
                    gambler.addBet(bet);
                } else {
                    existingBet.setScore1(bet.getScore1());
                    existingBet.setScore2(bet.getScore2());
                }
            }

        }
        updateGambler(gambler);
    }


    /**
     * Used to create gambler, and also to create the GamblerRanking object
     * @param user
     * @param matchs
     * @return
     */
    public Key<Gambler> createGambler(User user, List<Match> matchs) {
        return createGambler(user, matchs, 0);
    }

    //TODO : remove once mocked users are removed
    public Key<Gambler> createGambler(User user, List<Match> matchs, int points) {
        Gambler gambler = new Gambler(user.getEmail());
        gambler.setPrenom(user.getPrenom());
        gambler.setNom(user.getNom());

        for (Match match : matchs) {
            Bet bet = new Bet(match.getId());
            gambler.addBet(bet);
        }

        Key<Gambler> toRet = gamblerRepository.saveGambler(gambler);
        GamblerRanking gamblerRanking = new GamblerRanking(toRet.getId(),user.getNom(),user.getPrenom());
        gamblerRanking.setPoints(points);
        rankingDao.createUpdate(gamblerRanking);

        return toRet;
    }

    public Gambler updateGambler(Gambler gambler) {
        Key<Gambler> key = gamblerRepository.saveGambler(gambler);
        return gamblerRepository.getGambler(key);
    }

    public Gambler getGambler(Key<Gambler> gamblerKey) {
        return gamblerRepository.getGambler(gamblerKey);
    }

    private void calculateScores(Match match,boolean add) {
        List<Gambler> gamblers = gamblerRepository.getAll();
        for (Gambler gambler : gamblers) {
            Bet bet = getBetByMatchId(gambler, match.getId());
            if (bet !=null && bet.wasMade()) {
                GamblerRanking gamblerRanking = rankingDao.findByGambler(gambler.getId());
                //if gamblerRanking doesn't exist yet, we create it
                if(gamblerRanking==null){
                    gamblerRanking = new GamblerRanking(gambler.getId(),gambler.getNom(),gambler.getPrenom());
                }
                int points = CalculateScores.calculateScores(bet,match);
                if(points>0){
                    if(add){
                        gamblerRanking.addPoints(points);
                    }
                    else{
                        gamblerRanking.removePoints(points);
                    }
                }
                rankingDao.createUpdate(gamblerRanking);
            }
        }
    }

    public void setScore(Match match) {
        Match former = matchService.getMatch(match.getId());
        //Remove points which were added thanks to that match if the match already had a result
        if(former.isScoreUpdated()){
            this.calculateScores(former,false);
        }
        //registering the new result
        // We re-register the former value of the match with the new score values only, to make sure that nothing
        // else is updated in the match. We also set the updated property to true
        former.setScoreUpdated(true);
        former.setScore1(match.getScore1());
        former.setScore2(match.getScore2());
        matchService.createUpdate(former);
        //calculate scores again with the new match result
        this.calculateScores(match,true);
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
                GamblerRanking gamblerRanking = rankingDao.findByGambler(gambler.getId());
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
