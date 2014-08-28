package com.zenika.zenfoot.gae.rest;

import com.google.appengine.api.utils.SystemProperty;
import com.googlecode.objectify.Key;
import com.zenika.zenfoot.gae.dao.PWDLinkDAO;
import com.zenika.zenfoot.gae.dao.UserDao;
import com.zenika.zenfoot.gae.utils.PWDLink;
import com.zenika.zenfoot.gae.services.MockUserService;
import com.zenika.zenfoot.gae.utils.ResetPWD;
import com.zenika.zenfoot.user.User;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.PermitAll;
import restx.security.UserService;

import javax.inject.Named;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.List;
import java.util.Properties;


/**
 * Created by raphael on 18/08/14.
 */

@RestxResource
@Component
public class NewPasswordResource {

    private UserDao userDao;

    private PWDLinkDAO pwdLinkDAO;

    private MockUserService userService;

    public NewPasswordResource(UserDao userDao, PWDLinkDAO pwdLinkDAO, @Named("userService") UserService
            userService) {
        this.userDao = userDao;
        this.pwdLinkDAO = pwdLinkDAO;
        this.userService = (MockUserService) userService;
    }

    @POST("/generateLink")
    @PermitAll
    public void generateLink(User user) {
        User regUser = userDao.getUser(user.getEmail());

        if (regUser == null) {
            throw new WebException(HttpStatus.NOT_FOUND);
        }

        PWDLink pwdLink = new PWDLink(regUser.getId());

        Key<PWDLink> key = pwdLinkDAO.save(pwdLink);

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
            PWDLink pwdLink = pwdLinkDAO.get(resetPWD.getPwdLinkId());
            User user = userDao.getUser(pwdLink.getUserEmail());
            user.setPassword(resetPWD.getNewPWD());
            userService.createUser(user);
            pwdLinkDAO.delete(pwdLink.getId());

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
        PWDLink pwdLink = pwdLinkDAO.get(id);
        if (pwdLink == null) {
            throw new WebException(HttpStatus.NOT_FOUND);
        }
    }

    @GET("/cron/cleanPWDLinks")
    @PermitAll
    public void cleanPWDLinks() {
        List<PWDLink> pwdLinks = pwdLinkDAO.getAll();

        for (PWDLink pwdLink : pwdLinks) {
            if (pwdLink.mustBeRemoved()) {
                pwdLinkDAO.delete(pwdLink.getId());
            }
        }
    }
}
