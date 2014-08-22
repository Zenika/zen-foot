package com.zenika.zenfoot.gae.rest;

import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.exception.JsonWrappedErrorWebException;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.services.GamblerService;
import com.zenika.zenfoot.gae.services.MatchService;
import com.zenika.zenfoot.gae.services.MockUserService;
import com.zenika.zenfoot.user.User;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;
import restx.security.UserService;

import javax.inject.Named;
import java.util.Arrays;

/**
 * Created by raphael on 11/08/14.
 */
@RestxResource
@Component
public class SubscriptionResource {

    private MockUserService userService;
    private GamblerService gamblerService;
    private MatchService matchService;

    public SubscriptionResource(@Named("userService")UserService userService, GamblerService gamblerService, MatchService matchService) {
        this.userService = (MockUserService) userService;
        this.gamblerService = gamblerService;
        this.matchService = matchService;
    }

    @POST("/performSubscription")
    @PermitAll
    public void subscribe(UserAndTeams subscriber) {
        String email = subscriber.getUser().getEmail();
        User alreadyExistingUser = userService.getUserByEmail(email);

        if (alreadyExistingUser != null) {
            throw new JsonWrappedErrorWebException("SUBSCRIPTION_ERROR_ALREADY_USED_EMAIL",
                    String.format("L'email %s est déjà pris par un autre utilisateur !", email));
        }

        subscriber.getUser().setPassword(subscriber.getUser().getPasswordHash());
        subscriber.getUser().setRoles(Arrays.asList(Roles.GAMBLER));
        subscriber.getUser().setIsActive(Boolean.TRUE);

//        TODO Send mail
//        String subject = "Confirmation d'inscription à Zen Foot";
//        String urlConfirmation = "<a href='" + getUrlConfirmation() + subscriber.getUser().getEmail() + "'> Confirmation d'inscription </a>";
//        String messageContent = "Mr, Mme " + subscriber.getUser().getNom() + " Merci de cliquer sur le lien ci-dessous pour confirmer votre inscription. \n\n" + urlConfirmation;
//        subscriber.getUser().setIsActive(Boolean.FALSE);

        Key<User> keyUser = userService.createUser(subscriber.getUser());
        User user = userService.get(keyUser);
        Key<Gambler> gamblerKey = gamblerService.createGambler(user, matchService.getMatchs());

        Gambler gambler = gamblerService.getGambler(gamblerKey);
        gamblerService.addTeams(subscriber.getTeams(), gambler);
    }



    @GET("/confirmSubscription")
    @PermitAll
    public String confirmSubscription(String email) {
        final User user = userService.getUserByEmail(email);

        if (user != null && user.getIsActive() != null && !user.getIsActive()) {
            user.setIsActive(Boolean.TRUE);
            userService.updateUser(user);
            return Boolean.TRUE.toString();
        }

        return Boolean.FALSE.toString();
    }

}
