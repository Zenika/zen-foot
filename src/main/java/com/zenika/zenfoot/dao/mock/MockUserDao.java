package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.User;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockUserDao implements UserDao {
    private List<User> users = new ArrayList<User>();

    public List<User> find() {
        unser();
        List<User> nonPendingUsers = new ArrayList<User>();
        for (User user : users) {
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
        users.add(model);
        ser();
        return model;
    }

    private void ser() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("/tmp/zenfoot/users"));
            out.writeObject(users);
            out.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " ! CREATE DIRECTORY /tmp/zenfoot MANUALLY for it to work!");
        }
    }

    private void unser() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("/tmp/zenfoot/users"));
            users = (List<User>) in.readObject();
            in.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " ! CREATE DIRECTORY /tmp/zenfoot MANUALLY for it to work!");
        }
    }

    public List<User> findPending() {
        unser();
        List<User> pendingUsers = new ArrayList<User>();
        for (User user : users) {
            if (user.isPending()) {
                pendingUsers.add(user);
            }
        }
        return pendingUsers;
    }

    public void accept(User user) {
        get(user).setPending(false);
        ser();
    }

    public void reject(User user) {
        delete(user);
    }

    public void delete(User user) {
        unser();
        users.remove(user);
        ser();
    }

    private User get(User user) {
        unser();
        for (User u : users) {
            if (u.equals(user)) {
                return u;
            }
        }
        return null;
    }
}
