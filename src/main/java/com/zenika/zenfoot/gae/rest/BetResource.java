package com.zenika.zenfoot.gae.rest;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Pari;
import com.zenika.zenfoot.gae.services.BetService;
import com.zenika.zenfoot.user.User;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.RolesAllowed;
import restx.security.UserService;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

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
    @RolesAllowed(Roles.ADMIN)
    public String getMessage1() {
        return "Hello !";
    }

    @GET("/coucou")
    @RolesAllowed(Roles.GAMBLER)
    public String getCoucou() {
        return "coucou";
    }


}
