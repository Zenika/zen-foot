package fr.boillodmanuel.restx.gae.dao.token;

import java.security.SecureRandom;

public class Token {

	private byte[] token;

	private Token() {

	}
	
	private Token(String string){
		this.token = string.getBytes();
	}
	
	public Token(Token token){
		this.token = token.getToken();
	}

	public byte[] getToken() {
		return token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	public static Token generateToken() {
		byte[] tokenBytes = SecureRandom.getSeed(20);
		Token token = new Token();
		token.setToken(tokenBytes);
		return token;
	}
	
	public static Token getInstance(String string){
		return new Token(string);
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Token))return false;
		Token token2 = ((Token)o);
		String token2Str = new String(token2.getToken());
		String thisStr = new String (this.getToken());
		return (token2Str.equals(thisStr));
	}
	
	@Override
	public String toString(){
		return new String(this.getToken());
	}
}
