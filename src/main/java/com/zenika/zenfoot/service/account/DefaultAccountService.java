package com.zenika.zenfoot.service.account;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.User;
import com.zenika.zenfoot.service.email.EmailService;
import com.zenika.zenfoot.service.email.MockEmailService;
import com.zenika.zenfoot.util.StringUtil;

public class DefaultAccountService implements AccountService {
    public static final String VELOCITY_EMAILS = "velocity/emails/";
//    private transient UserDao userDao = new MockUserDao();
    private UserDao userDao;
    private transient EmailService emailService = new MockEmailService();
    private String adminEmail = "huber.olivier@gmail.com";
    private String appUrl = "http://zenfoot";

    @Override
    public void sendPassword(String userEmail) {
        String newPassword = StringUtil.getRandomCode();
        getUserDao().get(userEmail).setPassword(DigestUtils.md5Hex(newPassword));
        emailUserWithNewPassword(userEmail, newPassword);
    }

    @Override
    public void register(String userEmail, String password) {
        getUserDao().save(new User(userEmail, DigestUtils.md5Hex(password)));
        notifyUserWithRegistration(userEmail);
        notifyAdminWithRegistration(userEmail);
    }

    @Override
    public void accept(User user) {
        getUserDao().accept(user);
        notifyUserForAcceptance(user.getEmail());
    }

    @Override
    public void reject(User user) {
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
        emailService.sendEmailAsynchronously(adminEmail, userEmail, VELOCITY_EMAILS + "notifyRegistration", templateContext);
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
}
