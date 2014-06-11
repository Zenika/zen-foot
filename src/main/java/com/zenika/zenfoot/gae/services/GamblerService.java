package com.zenika.zenfoot.gae.services;

import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dao.RankingDAO;
import com.zenika.zenfoot.gae.dao.TeamDAO;
import com.zenika.zenfoot.gae.model.*;
import com.zenika.zenfoot.user.User;
import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by raphael on 30/04/14.
 */
public class GamblerService {

    private GamblerRepository gamblerRepository;
    private MatchService matchService;
    private TeamDAO teamDAO;
    protected RankingDAO rankingDao;

    public GamblerService(GamblerRepository gamblerRepository, MatchService matchService, TeamDAO teamDAO, RankingDAO rankingDAO) {
        this.matchService = matchService;
        this.gamblerRepository = gamblerRepository;
        this.teamDAO = teamDAO;
        this.rankingDao=rankingDAO;
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

    public Bet getBetByMatchId(Gambler gambler, Long matchId) {
        for (Bet bet : gambler.getBets()) {
            if (bet.getMatchId().equals(matchId)) {
                return bet;
            }
        }
        return null;
    }

    public void updateBets(List<Bet> newBets, Gambler gambler) {
        for (Bet bet : newBets) {
            Bet existingBet = getBetByMatchId(gambler, bet.getMatchId());
            //Check the bet already existed in the database
            if (existingBet == null) {
                gambler.addBet(bet);
            } else {
                existingBet.setScore1(bet.getScore1());
                existingBet.setScore2(bet.getScore2());
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

    //TODO : remove once the mocked users are removed
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

    public void calculateScores(Match match) {
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
                    gamblerRanking.addPoints(points);
                }
                rankingDao.createUpdate(gamblerRanking);
            }
        }
    }

    public Key<Gambler> addTeams(List<Team> teams, Gambler gambler) {
        Logger logger = Logger.getLogger(GamblerService.class.getName());


        Set<StatutTeam> toReg = new HashSet<>();
        for (Team team : teams) {
            Optional<Team> optTeam = teamDAO.get(team.getName());

            Team toRegister;
            boolean owner = false;

            if (optTeam.isPresent()) { // Team has already been created
                toRegister = optTeam.get();
                logger.log(Level.INFO, "Id for team : " + toRegister.getId());
            } else { //The team was created by the user and thus, the latter is the owner of it
                logger.log(Level.INFO, "No team found");
                team.setOwnerEmail(gambler.getEmail());
                Key<Team> teamKey = teamDAO.createUpdate(team);
                toRegister = teamDAO.get(teamKey);
                owner = true;
            }
            toReg.add(new StatutTeam().setTeam(toRegister).setAccepted(owner));

        }
//        gambler.addTeams(toReg);
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
    private Set<Team> isOwnerOf(Gambler gambler) {
        Set<Team> set = new HashSet<>();
//        for(StatutTeam statutTeam:gambler.getStatutTeams()){
//            Team team = statutTeam.getTeam();
//            if(team.getOwnerEmail().equals(gambler.getEmail())){
//                set.add(team);
//            }
//        }
        return set;
    }

    /**
     * Get all the
     *
     * @param gambler
     * @return
     */
    public Set<Gambler> wantToJoin(Gambler gambler) {
        Set<Team> ownedTeams = isOwnerOf(gambler);
        Set<Gambler> joining = new HashSet<>();

        for (Team team : ownedTeams) {
            joining.addAll(gamblerRepository.wantToJoin(team.getName()));
        }

        return joining;
    }


}
