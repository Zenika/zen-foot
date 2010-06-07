package com.zenika.zenfoot;

import java.util.Locale;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.User;

public class ZenFootSession extends AuthenticatedWebSession {
    private static Logger logger = LoggerFactory.getLogger(ZenFootSession.class);
//    private transient UserDao userDao = new MockUserDao();
    @SpringBean
    private UserDao userDao;
    private User user = null;

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
        User u = getUserDao().get(email);
        if (u != null && !u.isPending() && u.getPassword().equals(DigestUtils.md5Hex(password))) {
            user = getUserDao().get(email);
            dirty();
            return true;
        } else {
            return false;
        }
    }

    public User getUser() {
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

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
}
