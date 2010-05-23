package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MockUserDao implements UserDao {
    private List<User> users = new ArrayList<User>();

    public MockUserDao() {
        users.add(user("maghen@zenika.com", 20));
        users.add(user("loic@zenika.com", 19));
        users.add(user("olivier@zenika.com", 17));
        users.add(user("melodie@zenika.com", 15));
        users.add(user("carl@zenika.com", 11));
        users.add(user("pierre@zenika.com", 8));
        users.add(user("ophelie@zenika.com", 5));
        users.add(user("vincent@zenika.com", 4));
        users.add(user("laurent@zenika.com", 0));
    }

    public List<User> find() {
        return users;
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
            System.out.println(ex.getMessage()+" ! CREATE DIRECTORY /tmp/zenfoot MANUALLY for it to work!");
        }
    }

    private void unser() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("/tmp/zenfoot/users"));
            users = (List<User>) in.readObject();
            in.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+" ! CREATE DIRECTORY /tmp/zenfoot MANUALLY for it to work!");
        }
    }
}
