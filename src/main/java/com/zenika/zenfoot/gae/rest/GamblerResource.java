package com.zenika.zenfoot.gae.rest;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.ZenfootUserService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import com.zenika.zenfoot.gae.model.User;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.PUT;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.RolesAllowed;
import restx.security.UserService;

import javax.inject.Named;
import java.util.List;

/**
 * Created by raphael on 11/08/14.
 */
@RestxResource
@Component
public class GamblerResource {

    private GamblerService gamblerService;

    private SessionInfo sessionInfo;

    private ZenfootUserService userService;

    public GamblerResource(@Named("gamblerService") GamblerService gamblerService, @Named("sessioninfo") SessionInfo sessionInfo, 
            @Named("userService") UserService userService) {
        this.gamblerService = gamblerService;
        this.sessionInfo = sessionInfo;
        this.userService = (ZenfootUserService) userService;
    }

    @PUT("/gambler")
    @RolesAllowed(Roles.GAMBLER)
    public List<Object> updateGambler2(Gambler newGambler) {
/*
        User user = sessionInfo.getUser();
        Gambler gambler = gamblerService.get(user);
        List<Object> userGambler = new ArrayList<>();

        //Updating the gambler when the name changed
        if (!newGambler.getName().equals(gambler.getName()) || !newGambler.getFirstName().equals(gambler.getFirstName())) {
            GamblerRanking gamblerRanking = rankingDAO.findByGambler(gambler.getId());
            String prenom = newGambler.getFirstName();
            String nom = newGambler.getName();

            user.setFirstName(prenom);
            user.setName(nom);
            gambler.setFirstName(prenom);
            gambler.setLastName(nom);
            gamblerRanking.setLastName(nom);
            gamblerRanking.setFirstName(prenom);
            Key<User> userKey = userService.createUser(user);
            User userRet = userService.get(userKey);
            Gambler gamblerRet = gamblerService.updateGambler(gambler);
            rankingDAO.createUpdate(gamblerRanking);

            userGambler.add(userRet);
            userGambler.add(gamblerRet);
        }
        // Simply updating with new value
        else{
            gambler.setStatutTeams(newGambler.getStatutTeams());
            Gambler gamblerRetrieved = gamblerService.updateGambler(gambler);
            userGambler.add(user);
            userGambler.add(gamblerRetrieved);
        }
        return userGambler;*/
        return null;
    }
}
