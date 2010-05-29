package com.zenika.zenfoot;

import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.dao.mock.MockUserDao;
import com.zenika.zenfoot.model.User;
import java.util.Locale;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZenFootSession extends AuthenticatedWebSession {
    private static Logger logger = LoggerFactory.getLogger(ZenFootSession.class);
    private transient UserDao userDao = new MockUserDao();
    private User user = null;

    public ZenFootSession(Request request) {
        super(request);
        setLocale(Locale.FRENCH);
//        InjectorHolder.getInjector().inject(this);
    }

    public static ZenFootSession get() {
        return (ZenFootSession) AuthenticatedWebSession.get();
    }

    @Override
    public boolean authenticate(String email, String password) {
        User u = userDao.get(email);
        if (u.getPassword().equals(DigestUtils.md5Hex(password))) {
            user = u;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Roles getRoles() {
        Roles roles = new Roles();
        if (isSignedIn()) {
            roles.add(user.isAdmin() ? Roles.ADMIN : Roles.USER);
        }
        return roles;
    }
}
