package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.User;
import java.util.ArrayList;
import java.util.List;
import static com.zenika.zenfoot.dao.mock.MockUtil.users;
import static com.zenika.zenfoot.dao.mock.MockUtil.persist;

public class MockUserDao implements UserDao {
    public List<User> find() {
        List<User> nonPendingUsers = new ArrayList<User>();
        for (User user : users()) {
            if (!user.isPending()) {
                nonPendingUsers.add(user);
            }
        }
        return nonPendingUsers;
    }

    public static User user(String email, int points) {
        User user = new User(email);
        user.setPoints(points);
        return user;
    }

    public User save(User model) {
        if (!users().contains(model)) {
            users().add(model);
        } else {
            get(model).setAdmin(model.isAdmin());
        }
        persist();
        return model;
    }

    public List<User> findPending() {
        List<User> pendingUsers = new ArrayList<User>();
        for (User user : users()) {
            if (user.isPending()) {
                pendingUsers.add(user);
            }
        }
        return pendingUsers;
    }

    public void accept(User user) {
        get(user).setPending(false);
        persist();
    }

    public void reject(User user) {
        delete(user);
    }

    public void delete(User user) {
        users().remove(user);
        persist();
    }

    private User get(User user) {
        for (User u : users()) {
            if (u.equals(user)) {
                return u;
            }
        }
        return null;
    }

    public User get(String email) {
        return get(new User(email));
    }
}
