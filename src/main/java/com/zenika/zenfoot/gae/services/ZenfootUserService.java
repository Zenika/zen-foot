package com.zenika.zenfoot.gae.services;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.dao.UserDAO;
import com.zenika.zenfoot.gae.exception.JsonWrappedErrorWebException;
import com.zenika.zenfoot.gae.model.User;
import com.zenika.zenfoot.gae.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.security.UserRepository;
import restx.security.UserService;

public class ZenfootUserService extends AbstractGenericService<User>  implements UserService<User>, UserRepository<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZenfootUserService.class);

    public ZenfootUserService(UserDAO userDao) {
        super(userDao);
    }
    
    @Override
    public Optional<User> findUserByName(String email) {
        if (email != null) {
            User user = this.getUserbyEmail(email);
            return Optional.fromNullable(user);
        } else {
            return Optional.absent();
        }
    }

    public User getUserbyEmail(String email) {
        return ((UserDAO)this.getDao()).getUser(email);
    }

    /**
     * Try to find a user based on email and check password. Also checks that the user is activated.
     * @param email email
     * @param passwordHash pwd
     * @return user found
     */
    @Override
    public Optional<User> findAndCheckCredentials(String email, String passwordHash) {
        Optional<User> optionalUser = this.findUserByName(email);

        if (!optionalUser.isPresent()) {
            return optionalUser;
        }

        String credentials = optionalUser.get().getPasswordHash();
        // user need to provide good pwd and to have validated its account
        if (credentials.equals(PasswordUtils.getPasswordHash(passwordHash)) && (optionalUser.get().getIsActive() == null || optionalUser.get().getIsActive())) {
            return optionalUser;
        } else {
            return Optional.absent();
        }
    }

    public void resetPWD(String userEmail,String oldPW, String newPW) {
        Optional<User> userOpt = this.findAndCheckCredentials(userEmail, oldPW);
        if(!userOpt.isPresent()){
            throw new JsonWrappedErrorWebException("WRONG_PWD","le mot de passe renseigné n'est pas le bon");
        }
        else{
            User user = userOpt.get();
            user.hashAndSetPassword(newPW);
            this.createOrUpdate(user);
        }
    }
    
    @Override
    public Optional<String> findCredentialByUserName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAdminDefined() {
        return true;
    }

    @Override
    public User defaultAdmin() {
        return this.getUserbyEmail("admin@zenika.com");
    }
}
