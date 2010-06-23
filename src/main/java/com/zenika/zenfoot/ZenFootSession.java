package com.zenika.zenfoot;

import com.zenika.zenfoot.dao.MessageDao;
import java.util.Locale;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zenika.zenfoot.dao.PlayerDao;
import com.zenika.zenfoot.model.Player;

public class ZenFootSession extends AuthenticatedWebSession {

    private static Logger logger = LoggerFactory.getLogger(ZenFootSession.class);
    @SpringBean
    private PlayerDao userDao;
    @SpringBean
    private MessageDao messageDao;
    long lastMessageId;
    private Player user = null;

    public ZenFootSession(Request request) {
        super(request);
        setLocale(Locale.FRENCH);
        InjectorHolder.getInjector().inject(this);
    }

    public static ZenFootSession get() {
        return (ZenFootSession) AuthenticatedWebSession.get();
    }

    @Override
    public void signOut() {
        super.signOut();
        user = null;
        dirty();
    }

    @Override
    public boolean authenticate(String email, String password) {
        Player u = userDao.find(email);
        if (u != null && !u.isPending() && u.getPassword().equals(DigestUtils.md5Hex(password))) {
            user = userDao.find(email);
            dirty();
            return true;
        } else {
            return false;
        }
    }

    public Player getUser() {
        return user;
    }

    @Override
    public Roles getRoles() {
        Roles roles = new Roles();
        if (isSignedIn()) {
            roles.add(user.isAdmin() ? Roles.ADMIN : Roles.USER);
        }
        return roles;
    }

    public void setUserDao(PlayerDao userDao) {
        this.userDao = userDao;
    }

    public PlayerDao getUserDao() {
        return userDao;
    }

    public boolean newMessages() {
        return lastMessageId != messageDao.findLastOne().getId();
    }

    public void setLastMessage() {
        this.lastMessageId = messageDao.findLastOne().getId();
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
}
