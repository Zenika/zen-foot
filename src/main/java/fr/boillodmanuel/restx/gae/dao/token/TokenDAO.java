package fr.boillodmanuel.restx.gae.dao.token;

import com.googlecode.objectify.ObjectifyService;

public class TokenDAO {

	public void save(Token token){
		ObjectifyService.register(Token.class);
		ObjectifyService.ofy().save().entity(token).now();
	}
	
	public void delete(Token token){
		
	}
}
