package com.zenika.zenfoot.gae.rest;

import org.joda.time.DateTime;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;

import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import restx.security.RestxSession;
import restx.security.RolesAllowed;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.domain.Message;
import com.zenika.zenfoot.gae.domain.Personne;

@Component
@RestxResource
public class HelloResource {

	/**
	 * Say hello to currently logged in user.
	 * 
	 * Authorized only for principals with Roles.HELLO_ROLE role.
	 * 
	 * @return a Message to say hello
	 */
	@GET("/message")
	@RolesAllowed(Roles.HELLO_ROLE)
	public Message sayHello() {
		return new Message().setMessage(String.format("hello %s, it's %s",
				RestxSession.current().getPrincipal().get().getName(), DateTime
						.now().toString("HH:mm:ss")));
	}

	/**
	 * Say hello to anybody.
	 * 
	 * Does not require authentication.
	 * 
	 * @return a Message to say hello
	 */
	@GET("/hello")
	@PermitAll
	public Message helloPublic(String who) {
		Personne personne;

		if (!who.equals("") && who != null) {
			personne = new Personne().setPersonne(who);
			ObjectifyService.register(Personne.class);
			ObjectifyService.ofy().save().entity(personne).now();
		} else {
			QueryResultIterator<Personne> results = ObjectifyService.ofy()
					.load().type(Personne.class).iterator();
			if (results.hasNext()) {
				personne = results.next();
			} else {
				personne = new Personne().setPersonne("");
			}
		}
		return new Message().setMessage(String.format("hello %s, it's %s",
				personne.getPersonne(), DateTime.now().toString("HH:mm:ss")));
	}
}
