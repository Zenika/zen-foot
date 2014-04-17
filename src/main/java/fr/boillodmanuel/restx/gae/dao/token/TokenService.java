package fr.boillodmanuel.restx.gae.dao.token;

import java.util.ArrayList;

import restx.factory.Component;

@Component
public class TokenService {
	
	private ArrayList<Token> tokens = new ArrayList<Token>();
	
	public TokenService(){
		
	}
	
	/**
	 * Says whether the token is valid or not
	 * @param token
	 * @return true if the token is valid, else false
	 */
	public boolean isValid(Token token){
		return tokenExists(token);
	}
	
	/**
	 * Says whether the token is contained in the list. This is just for the simple proof of concept.
	 * Later this method should look for the token in the database.
	 * @param token
	 * @return
	 */
	public boolean tokenExists(Token token){
		return tokens.contains(token);
		
	}
	
	/**
	 * Adds a token to the list of existing tokens
	 * @param token
	 */
	public void addToken(Token token){
		this.tokens.add(token);
	}

}
