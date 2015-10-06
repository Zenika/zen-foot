package com.zenika.zenfoot.gae.rest;

import com.zenika.zenfoot.gae.AppInfoService;
import com.zenika.zenfoot.gae.dto.UserAndTeams;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.exception.JsonWrappedErrorWebException;
import com.zenika.zenfoot.gae.services.MailSenderService;
import com.zenika.zenfoot.gae.services.ZenfootUserService;
import com.zenika.zenfoot.gae.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.PermitAll;
import restx.security.UserService;

import javax.inject.Named;
import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by raphael on 11/08/14.
 */
@RestxResource
@Component
public class SubscriptionResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionResource.class);

    private ZenfootUserService userService;
    private MailSenderService mailSenderService;
    private AppInfoService appInfoService;

    public SubscriptionResource(@Named("userService") UserService userService,
                                MailSenderService mailSenderService, AppInfoService appInfoService) {
        this.userService = (ZenfootUserService) userService;
        this.mailSenderService = mailSenderService;
        this.appInfoService = appInfoService;
    }

    /**
     * Try to perform subscription for the user. If user already exists, an exception is thrown.
     * @param subscriber subscriber
     */
    @POST("/performSubscription")
    @PermitAll
    public void subscribe(UserAndTeams subscriber) {
        String email = subscriber.getUser().getEmail();
        User alreadyExistingUser = userService.getFromID(email);

        if (alreadyExistingUser != null) {
            throw new JsonWrappedErrorWebException("SUBSCRIPTION_ERROR_ALREADY_USED_EMAIL",
                    String.format("L'email %s est déjà pris par un autre utilisateur !", email));
        }
        if(subscriber.getUser().getPasswordHash() == null){
            throw new JsonWrappedErrorWebException("SUBSCRIPTION_ERROR_INVALID_PASSWORD", "Password cannot be null");
        }

        subscriber.getUser().hashAndSetPassword(subscriber.getUser().getPasswordHash());
        subscriber.getUser().setRoles(Arrays.asList(Roles.GAMBLER));
        subscriber.getUser().setActivationToken(("someRandomToken" + new Date()).hashCode());
        subscriber.getUser().setIsActive(Boolean.FALSE);

        String subject = "Confirmation d'inscription à Zen Foot";
        String urlConfirmation = "<a href='" + getUrlConfirmation(subscriber.getUser()) + "'> Confirmation d'inscription </a>";
        String messageContent = "Mr, Mme " + subscriber.getUser().getName() + "\n Merci de cliquer sur le lien ci-dessous pour confirmer votre inscription. \n\n" + urlConfirmation;
        try {
            mailSenderService.sendMail(email, subject, messageContent);
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, "Error sending confirmation mail");
        }

        userService.createOrUpdate(subscriber.getUser());
    }

    private String getUrlConfirmation(User user) {
        return appInfoService.getAppUrl() + "/#/confirmSubscription?email=" + user.getEmail() + "&token=" + user.getActivationToken();
    }


    @GET("/confirmSubscription")
    @PermitAll
    public void confirmSubscription(String email, String token) {
        Integer tknInt;
        try {
            tknInt = Integer.valueOf(token);
        }catch(NumberFormatException e){
            throw new WebException(HttpStatus.BAD_REQUEST, "Wrong token.");
        }

        final User user = userService.getFromID(email);
        if (user == null) {
            throw new WebException(HttpStatus.BAD_REQUEST, "Wrong email.");
        }else if (!user.getActivationToken().equals(tknInt)) {
            throw new WebException(HttpStatus.BAD_REQUEST, "Wrong token.");
        }else if (user.getIsActive() == null || !user.getIsActive()){
            user.setIsActive(Boolean.TRUE);
            userService.createOrUpdate(user);
        }
    }

}
