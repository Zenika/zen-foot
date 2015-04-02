package com.zenika.zenfoot.gae.rest;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.MockUserService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import com.zenika.zenfoot.user.User;
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

    private MockUserService userService;

    public GamblerResource(GamblerService gamblerService, @Named("sessioninfo") SessionInfo sessionInfo, 
            @Named("userService") UserService userService) {
        this.gamblerService = gamblerService;
        this.sessionInfo = sessionInfo;
        this.userService = (MockUserService) userService;
    }

    @POST("/gamblers")
    @RolesAllowed(Roles.GAMBLER)
    public Gambler updateGambler(GamblerAndTeams gamblerAndTeams) {
        Key<Gambler> gamblerKey = gamblerService.addTeams(gamblerAndTeams.getTeams(), gamblerAndTeams.getGambler());
        return gamblerService.getGambler(gamblerKey);
    }

    @PUT("/gambler")
    @RolesAllowed(Roles.GAMBLER)
    public List<Object> updateGambler2(Gambler newGambler) {
/*
        User user = sessionInfo.getUser();
        Gambler gambler = gamblerService.get(user);
        List<Object> userGambler = new ArrayList<>();

        //Updating the gambler when the name changed
        if (!newGambler.getNom().equals(gambler.getNom()) || !newGambler.getPrenom().equals(gambler.getPrenom())) {
            GamblerRanking gamblerRanking = rankingDAO.findByGambler(gambler.getId());
            String prenom = newGambler.getPrenom();
            String nom = newGambler.getNom();

            user.setPrenom(prenom);
            user.setName(nom);
            gambler.setPrenom(prenom);
            gambler.setNom(nom);
            gamblerRanking.setNom(nom);
            gamblerRanking.setPrenom(prenom);
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



    @POST("/changePW")
    @RolesAllowed(Roles.GAMBLER)
    public void changePW(List<String> pwds){
        String oldPW = pwds.get(0);
        String newPW = pwds.get(1);
        userService.resetPWD(sessionInfo.getUser().getEmail(),oldPW,newPW);
    }

    @GET("/gambler")
    //@JsonView(Views.GamblerView.class)
    @RolesAllowed(Roles.GAMBLER)
    public Gambler getGambler() {
        User user = sessionInfo.getUser();
        return null;
    }

    @GET("/gamblers/{id}")
    @RolesAllowed({Roles.GAMBLER, Roles.ADMIN})
    public Gambler getGambler(Long id) {
        return gamblerService.get(id);
    }

    @GET("/gamblers")
    @RolesAllowed(Roles.GAMBLER)
    public List<Gambler> getGamblers() {
        return gamblerService.getAll();
    }
}
