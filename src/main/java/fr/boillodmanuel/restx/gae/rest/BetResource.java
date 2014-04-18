package fr.boillodmanuel.restx.gae.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import fr.boillodmanuel.restx.gae.AppModule;
import fr.boillodmanuel.restx.gae.services.MockUserService;
import fr.boillodmanuel.restx.gae.services.MockZenFootUserRepository;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;

import com.google.common.base.Optional;

import fr.boillodmanuel.restx.gae.model.Pari;
import fr.boillodmanuel.restx.gae.services.BetService;
import fr.boillodmanuel.restx.user.User;
import restx.security.RolesAllowed;
import restx.security.UserService;

@RestxResource
@Component
public class BetResource {

	private BetService betService;

	private UserService<User> userService;

	public BetResource(@Named("bets") BetService betService,
			@Named("userService") UserService userService) {
		this.betService = betService;
		this.userService = userService;
	}

	@GET("/paris")
	public List<Pari> getParis(String name) {
		Optional<User> userOpt = userService.findUserByName(name);

		List<Pari> toRet = new ArrayList<>();

		if (userOpt.isPresent() && (userOpt.get() != null)) {
			toRet = betService.getBets(userOpt.get());
		}
		return toRet;
	}

    @GET("/hello")
    @RolesAllowed(AppModule.ADMIN)
    public String getMessage1(){
        return "Hello !";
    }

    @GET("/coucou")
    @RolesAllowed(AppModule.USER)
    public String getCoucou(){
        return "coucou";
    }

}
