package com.zenika.zenfoot.gae.model;

/**
 * This is an implementation of a match bet.
 * <p/>
 * Created by raphael on 28/04/14.
 */
public class Bet {

    private Long matchId;
    private Integer score1;
    private Integer score2;

    private Sport typeSport;
    
    public Bet() {
    }

    public Bet(Long matchId) {
        this.matchId = matchId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    @Override
    public String toString() {
        return " match " + matchId + ": " + score1 + " - " + score2;
    }


    public boolean wasMade() {
        return this.score1!=null && this.score2!=null;
    }

	/**
	 * @return the typeSport
	 */
	public Sport getTypeSport() {
		return typeSport;
	}

	/**
	 * @param typeSport the typeSport to set
	 */
	public void setTypeSport(Sport typeSport) {
		this.typeSport = typeSport;
	}
}
