package com.zenika.zenfoot.gae.rest;

import com.google.appengine.api.utils.SystemProperty;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.services.PWDLinkService;
import com.zenika.zenfoot.gae.services.ZenfootUserService;
import com.zenika.zenfoot.gae.services.SessionInfo;
import com.zenika.zenfoot.gae.utils.PWDLink;
import com.zenika.zenfoot.gae.utils.ResetPWD;
import com.zenika.zenfoot.user.User;
import restx.RestxRequest;
import restx.RestxResponse;
import restx.WebException;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.RolesAllowed;

import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import restx.annotations.GET;
import restx.security.PermitAll;


@RestxResource
@Component
public class UserResource {

    final private ZenfootUserService userService;
    final private SessionInfo sessionInfo;
    final private PWDLinkService pWDLinkService;
    
    public UserResource(@Named("sessioninfo") SessionInfo sessionInfo, 
            @Named("zenfootUserService") ZenfootUserService userService, PWDLinkService pWDLinkService) {
        this.userService = userService;
        this.sessionInfo = sessionInfo;
        this.pWDLinkService = pWDLinkService;
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
            throw new WebException(HttpStatus.NOT_FOUND);
        }

        PWDLink pwdLink = new PWDLink(regUser.getId());

        Key<PWDLink> key = this.pWDLinkService.createOrUpdate(pwdLink);

        String domain = "http://";

        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
            domain += "localhost:9000/";
        } else {
            domain += SystemProperty.applicationId.get() + ".appspot.com";
        }

        String urlToSend = domain + "#/reset_password/" + key.getId();

        try {
            sendMail(urlToSend, regUser.getEmail());
        } catch (Exception e) {
            throw new WebException(HttpStatus.NOT_FOUND);
        }

    }

    public void sendMail(String urlToSend, String destEmail) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "Pour réinitialiser votre mot de passe, veuillez cliquer sur le lien suivant : ";
        msgBody += '\n' + urlToSend;

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("raphael.martignoni@zenika.com", "Admin Zenfoot"));
        msg.addRecipient(Message.RecipientType.TO,
                new InternetAddress(destEmail));
        msg.setSubject(MimeUtility.encodeText("Réinitialisation de votre mot de passe zenfoot", "UTF-8", "Q"));
        msg.setText(msgBody);
        Transport.send(msg);
    }

    @POST("/resetPWD")
    @PermitAll
    public void resetPWD(ResetPWD resetPWD) {

        try {
            PWDLink pwdLink = this.pWDLinkService.getFromID(resetPWD.getPwdLinkId());
            User user = userService.getUserbyEmail(pwdLink.getUserEmail());
            user.setPassword(resetPWD.getNewPWD());
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
