package com.zenika.zenfoot.gae.rest;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Score;

/**
 * Created by raphael on 30/04/14.
 */
public class MatchAndBet {

    private Bet bet;

    private Match match;

    public MatchAndBet() {
    }

    public Bet getBet() {
        return bet;
    }

    public MatchAndBet setBet(Bet bet) {

        /*
        Optional<Score> unGivenScore = checkBet(bet);
        if(unGivenScore.isPresent()){
            unGivenScore.get().setScore(0);
        }
        */
        this.bet = bet;
        return this;
    }

    /**
     * Checks that the user gave a result for both teams. If not (i.e. one Score instance has a -1 score), returns
     * an optional of the Score object which wasn't rateed. If things are OK, returns an absent optional
     * @return the Score which has a -1 score
     * @param bet
     */
    public Optional<Score> checkBet(Bet bet){
        Score toRet = null;
        if(bet.getScore1().isUnknown()&&!bet.getScore2().isUnknown()){
            toRet=bet.getScore1();
        }
        else{
            if(!bet.getScore1().isUnknown()&&bet.getScore2().isUnknown()){
                toRet=bet.getScore2();
            }
        }

        Optional<Score> toRetOpt = Optional.fromNullable(toRet);
        return toRetOpt;

    }

    public Match getMatch() {
        return match;
    }

    public MatchAndBet setMatch(Match match) {
        this.match = match;
        return this;
    }
}
