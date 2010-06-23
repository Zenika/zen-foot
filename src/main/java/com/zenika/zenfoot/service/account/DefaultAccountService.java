package com.zenika.zenfoot.service.account;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.zenika.zenfoot.dao.PlayerDao;
import com.zenika.zenfoot.model.Player;
import com.zenika.zenfoot.service.email.EmailService;
import com.zenika.zenfoot.util.StringUtil;

public class DefaultAccountService implements AccountService {

    public static final String VELOCITY_EMAILS = "velocity/emails/";
    private PlayerDao userDao;
    private EmailService emailService;
    private String adminEmail = "huber.olivier@gmail.com";
    private String appUrl = "http://zenfoot";

    public DefaultAccountService() {
    }

    @Override
    public void sendPassword(String userEmail) {
        String newPassword = StringUtil.getRandomCode();
        final Player player = userDao.find(userEmail);
        player.setPassword(DigestUtils.md5Hex(newPassword));
        userDao.save(player);
        emailUserWithNewPassword(userEmail, newPassword);
    }

    @Override
    public void register(String userEmail, String password) {
        //TODO REINSTAURER L'INSCRIPTION !!!
        final Player newAutoInscriptionPlayer = new Player(userEmail, DigestUtils.md5Hex(password));
        newAutoInscriptionPlayer.setPending(false);
        userDao.save(newAutoInscriptionPlayer);
//        notifyUserWithRegistration(userEmail);
        notifyAdminWithRegistration(userEmail);
    }

    @Override
    public void accept(Player user) {
        userDao.accept(user);
        notifyUserForAcceptance(user.getEmail());
    }

    @Override
    public void reject(Player user) {
        userDao.delete(user);
        notifyUserForRejection(user.getEmail());
    }

    @Override
    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    private void emailUserWithNewPassword(String userEmail, String newPassword) {
        Map<String, Object> templateContext = new HashMap<String, Object>();
        templateContext.put("appUrl", appUrl);
        templateContext.put("userEmail", userEmail);
        templateContext.put("password", newPassword);
        emailService.sendEmailAsynchronously(userEmail, adminEmail, VELOCITY_EMAILS + "renewPassword", templateContext);
    }

    private void notifyUserWithRegistration(String userEmail) {
        Map<String, Object> templateContext = new HashMap<String, Object>();
        templateContext.put("appUrl", appUrl);
        templateContext.put("userEmail", userEmail);
        emailService.sendEmailAsynchronously(userEmail, adminEmail, VELOCITY_EMAILS + "registerUser", templateContext);
    }

    private void notifyAdminWithRegistration(String userEmail) {
        Map<String, Object> templateContext = new HashMap<String, Object>();
        templateContext.put("appUrl", appUrl);
        templateContext.put("userEmail", userEmail);
        emailService.sendEmailAsynchronously(adminEmail, adminEmail, VELOCITY_EMAILS + "notifyRegistration", templateContext);
    }

    private void notifyUserForAcceptance(String userEmail) {
        Map<String, Object> templateContext = new HashMap<String, Object>();
        templateContext.put("appUrl", appUrl);
        templateContext.put("userEmail", userEmail);
        emailService.sendEmailAsynchronously(userEmail, adminEmail, VELOCITY_EMAILS + "acceptUser", templateContext);
    }

    private void notifyUserForRejection(String userEmail) {
        Map<String, Object> templateContext = new HashMap<String, Object>();
        templateContext.put("appUrl", appUrl);
        templateContext.put("userEmail", userEmail);
        emailService.sendEmailAsynchronously(userEmail, adminEmail, VELOCITY_EMAILS + "rejectUser", templateContext);
    }

    @Override
    public void feedback(Player user, String message) {
        Map<String, Object> templateContext = new HashMap<String, Object>();
        templateContext.put("appUrl", appUrl);
        templateContext.put("userEmail", user.getEmail());
        templateContext.put("message", message);
        emailService.sendEmailAsynchronously(adminEmail, adminEmail, VELOCITY_EMAILS + "feedback", templateContext);
    }

    @Override
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public void setUserDao(PlayerDao userDao) {
        this.userDao = userDao;
    }

    public PlayerDao getUserDao() {
        return userDao;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
