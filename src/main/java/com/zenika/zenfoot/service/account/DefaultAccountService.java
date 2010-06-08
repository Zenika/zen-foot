package com.zenika.zenfoot.service.account;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.Player;
import com.zenika.zenfoot.service.email.EmailService;
import com.zenika.zenfoot.util.StringUtil;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class DefaultAccountService implements AccountService {

    public static final String VELOCITY_EMAILS = "velocity/emails/";
    private UserDao userDao;
    private EmailService emailService;
    private String adminEmail = "huber.olivier@gmail.com";
    private String appUrl = "http://zenfoot";

    public DefaultAccountService() {
    }

    @Override
    public void sendPassword(String userEmail) {
        String newPassword = StringUtil.getRandomCode();
        getUserDao().get(userEmail).setPassword(DigestUtils.md5Hex(newPassword));
        emailUserWithNewPassword(userEmail, newPassword);
    }

    @Override
    public void register(String userEmail, String password) {
        getUserDao().save(new Player(userEmail, DigestUtils.md5Hex(password)));
        notifyUserWithRegistration(userEmail);
        notifyAdminWithRegistration(userEmail);
    }

    @Override
    public void accept(Player user) {
        getUserDao().accept(user);
        notifyUserForAcceptance(user.getEmail());
    }

    @Override
    public void reject(Player user) {
        getUserDao().reject(user);
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
        emailService.sendEmailAsynchronously(userEmail, adminEmail, VELOCITY_EMAILS + "notifyRegistration", templateContext);
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
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}