/**
 * 
 */
package com.zenika.zenfoot.gae.common;

/**
 * @author vickrame
 *
 */
public enum SportEnum {

	FOOTBALL(1,"Foot"),RUGBY(2,"Rugby"),HANDBALL(3,"HandBall"),BASKET(4,"Basket"),TENNIS(5,"Tennis");
	
	private long  identifiantSport;
	private String nameSport;
	
	
	private SportEnum(long identifiantSport, String nomSport){
		this.identifiantSport = identifiantSport;
		this.nameSport = nomSport;
	}


	/**
	 * @return the identifiantSport
	 */
	public long getIdentifiantSport() {
		return identifiantSport;
	}


	/**
	 * @param identifiantSport the identifiantSport to set
	 */
	public void setIdentifiantSport(int identifiantSport) {
		this.identifiantSport = identifiantSport;
	}


	/**
	 * @return the nameSport
	 */
	public String getNameSport() {
		return nameSport;
	}


	/**
	 * @param nameSport the nameSport to set
	 */
	public void setNameSport(String nameSport) {
		this.nameSport = nameSport;
	}
	
	
}
