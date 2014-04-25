package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.Embedded;

/**
 * The abstract class representing the results of a match. It is extended by BetMatch and ActualResultMatch,
 * which represent respectively a bet on a match and an actual match.
 *
 * Created by raphael on 24/04/14.
 */

@Entity
public abstract class AbsResultMatch implements IResultMatch {

    @Id
    private Long id;
    private Match match;
    private Pays gagnant;
    @Embedded
    private Points points1;
    @Embedded
    private Points point2;

    public AbsResultMatch(Match match, Pays gagnant, Points points1, Points point2) {
        this.match = match;
        this.gagnant = gagnant;
        this.points1 = points1;
        this.point2 = point2;
    }

    public AbsResultMatch() {
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Pays getGagnant() {
        return gagnant;
    }

    public void setGagnant(Pays gagnant) {
        this.gagnant = gagnant;
    }


    public int getPoints(Pays participant){
        if(this.point2.getParticipant().equals(participant)){
            return this.point2.getPoint();
        }
        else{
            if(this.points1.getParticipant().equals(participant)){
                return this.points1.getPoint();
            }
            else{

                throw new RuntimeException("Unknown participant");

            }
        }
    }

    public void setPoints1(Points points1) {
        this.points1 = points1;
    }


    public void setPoint2(Points point2) {
        this.point2 = point2;
    }

    public boolean rightWinner(BetMatch betMatch){
        return betMatch.getGagnant().equals(this.getGagnant());
    }

    public boolean rightPoints(BetMatch betMatch){
        //TODO
        return false;
    }


    public static class Points {


        private int point;
        private Pays participant;

        public Points() {
        }

        public Points(int point, Pays participant) {
            this.point = point;
            this.participant = participant;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public Pays getParticipant() {
            return participant;
        }

        public void setParticipant(Pays participant) {
            this.participant = participant;
        }



    }


}
