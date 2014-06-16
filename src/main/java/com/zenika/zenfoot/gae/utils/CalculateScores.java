package com.zenika.zenfoot.gae.utils;

import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.gae.model.Match;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by raphael on 11/06/14.
 */
public class CalculateScores {

    public static boolean almostRight(Bet bet, Match result){
        //team1 wins
        boolean possBet1 = bet.getScore1()>bet.getScore2();
        boolean possMat1 = result.getScore1()>result.getScore2();
        boolean rightOn1 = possBet1&&possMat1;

        //team 2 wins
        boolean possBet3 = bet.getScore2()>bet.getScore1();
        boolean possMat3 = result.getScore2()>result.getScore1();
        boolean rightOn3 = possBet3&&possMat3;

        //equality
        boolean possBet2=bet.getScore2().equals(bet.getScore1());
        boolean possMat2 = result.getScore2().equals(result.getScore1());
        boolean rightOn2 = possBet2&&possMat2;
        return rightOn1||rightOn2||rightOn3;
    }

    public static boolean exactlyRight(Bet bet,Match result){
        Logger log = Logger.getLogger(CalculateScores.class.getName());

        return bet.getScore1().equals(result.getScore1())&&bet.getScore2().equals(result.getScore2());
    }

    public static int calculateScores(Bet bet, Match match){
        if(exactlyRight(bet,match)){
            return 3;
        }
        else{
            if(almostRight(bet,match)){
                return 1;
            }
            else{
                return 0;
            }
        }
    }
}
