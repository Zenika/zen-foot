package com.zenika.zenfoot.gae.rest;

import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.google.common.base.Optional;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.AppInfoService;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dto.BetDTO;
import com.zenika.zenfoot.gae.dto.UserDTO;
import com.zenika.zenfoot.gae.services.MailSenderService;
import com.zenika.zenfoot.gae.services.PWDLinkService;
import com.zenika.zenfoot.gae.services.ZenfootUserService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import com.zenika.zenfoot.gae.utils.PWDLink;
import com.zenika.zenfoot.gae.utils.ResetPWD;
import com.zenika.zenfoot.gae.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.RestxRequest;
import restx.RestxResponse;
import restx.WebException;
import restx.annotations.*;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.RolesAllowed;

import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import restx.security.PermitAll;


@RestxResource
@Component
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    final private ZenfootUserService userService;
    final private SessionInfo sessionInfo;
    final private PWDLinkService pWDLinkService;
    final private AppInfoService appInfoService;
    final private MailSenderService mailSenderService;

    public UserResource(@Named("sessioninfo") SessionInfo sessionInfo, 
            @Named("zenfootUserService") ZenfootUserService userService, PWDLinkService pWDLinkService, MailSenderService mailSenderService, AppInfoService appInfoService) {
        this.userService = userService;
        this.sessionInfo = sessionInfo;
        this.pWDLinkService = pWDLinkService;
        this.mailSenderService = mailSenderService;
        this.appInfoService = appInfoService;
    }

    @GET("/users")
    @RolesAllowed(Roles.ADMIN)
    public List<UserDTO> getAll(Optional<String> name){
        if(name.isPresent() && !StringUtil.isEmptyOrWhitespace(name.get())){
            return userService.getAllAsDTO(name.get());
        }
        return userService.getAllAsDTO();
    }

    @DELETE("/users/{id}")
    @RolesAllowed(Roles.ADMIN)
    public void delete(String id){
        userService.delete(id);
    }

    @PUT("/users/{id}/resetpwd")
    @RolesAllowed(Roles.ADMIN)
    public void resetPWD(String id){
        //TODO
        throw new WebException("Not supported.");
    }


    /**
     * Migrate users to use new properties:
     *  - name --> lastname
     *  - prenom --> firstname
     */
    @GET("/users/migratenameprops")
    @RolesAllowed(Roles.ADMIN)
    public void migrate(){
        userService.migrateNameProps();
    }


    @POST("/redirectAfterLogin")
    public void redirectAfterLogin() {
        throw new WebException(HttpStatus.FOUND) {
            @Override
            public void writeTo(RestxRequest restxRequest, RestxResponse restxResponse) throws IOException {
                restxResponse
                        .setStatus(getStatus())
                        .setHeader("Location", "/#/bets");
            }
        };
    }
    
    @POST("/changePW")
    @RolesAllowed(Roles.GAMBLER)
    public void changePW(List<String> pwds){
        String oldPW = pwds.get(0);
        String newPW = pwds.get(1);
        userService.resetPWD(sessionInfo.getUser().getEmail(),oldPW,newPW);
    }

    @POST("/generateLink")
    @PermitAll
    public void generateLink(User user) {
        User regUser = userService.getUserbyEmail(user.getEmail());

        if (regUser == null) {
            throw new WebException(HttpStatus.BAD_REQUEST, "No user for this mail");
        }

        PWDLink pwdLink = new PWDLink(regUser.getId());
        Key<PWDLink> key = this.pWDLinkService.createOrUpdate(pwdLink);
        try {
            sendPasswordMail(key, regUser.getEmail());
        } catch (Exception e) {
            LOGGER.error("Error sending mail: " + e.getMessage(), e);
            throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, "Error sending mail.");
        }

    }

    public void sendPasswordMail(Key<PWDLink> key, String destEmail) throws Exception {
        String urlToSend = appInfoService.getAppUrl() + "/#/reset_password/" + key.getId();
        String msgBody = "Pour réinitialiser votre mot de passe, veuillez cliquer sur le lien suivant : ";
        msgBody += '\n' + urlToSend;
        String subject = "Réinitialisation de votre mot de passe zenfoot";

        mailSenderService.sendMail(destEmail, subject, msgBody);
    }

    @POST("/resetPWD")
    @PermitAll
    public void resetPWD(ResetPWD resetPWD) {

        try {
            PWDLink pwdLink = this.pWDLinkService.getFromID(resetPWD.getPwdLinkId());
            User user = userService.getUserbyEmail(pwdLink.getUserEmail());
            user.hashAndSetPassword(resetPWD.getNewPWD());
            userService.createOrUpdate(user);
            pWDLinkService.delete(pwdLink.getId());

        } catch (NullPointerException e) {
            throw new WebException(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * This is used to check that the PWDLink object exists. It is used when a user accesses a page to reset
     * the password, with a PWDLink id that's no longer available (either because it is expired, or because
     * the user has already reset their password with it, or even because it simply never existed). The resource
     * is used to display a message to the client and inform them that the resource doesn't exist.
     *
     * @param id
     */
    @GET("/checkPWDLink/{id}")
    @PermitAll
    public void checkPWDLink(Long id) {
        PWDLink pwdLink = this.pWDLinkService.getFromID(id);
        if (pwdLink == null) {
            throw new WebException(HttpStatus.NOT_FOUND);
        }
    }

    @GET("/cron/cleanPWDLinks")
    @PermitAll
    public void cleanPWDLinks() {
        List<PWDLink> pwdLinks = this.pWDLinkService.getAll();

        for (PWDLink pwdLink : pwdLinks) {
            if (pwdLink.mustBeRemoved()) {
                this.pWDLinkService.delete(pwdLink.getId());
            }
        }
    }

}
