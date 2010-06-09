package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static com.zenika.zenfoot.dao.mock.MockUtil.users;
import static com.zenika.zenfoot.dao.mock.MockUtil.persist;

public class MockUserDao implements UserDao {
    public List<Player> find() {
        List<Player> nonPendingUsers = new ArrayList<Player>();
        for (Player user : users()) {
            if (!user.isPending()) {
                nonPendingUsers.add(user);
            }
        }
        Collections.sort(nonPendingUsers, new Comparator<Player>() {
            public int compare(Player u1, Player u2) {
                return u2.getPoints() - u1.getPoints();
            }
        });
        return nonPendingUsers;
    }

    public static Player user(String email, int points) {
        Player user = new Player(email);
        user.setPoints(points);
        return user;
    }

    public void save(Player model) {
        if (!users().contains(model)) {
            users().add(model);
        } else {
            get(model).setAdmin(model.isAdmin());
            get(model).setPoints(model.getPoints());
            get(model).setPending(model.isPending());
        }
        persist();
    }

    public List<Player> findPending() {
        List<Player> pendingUsers = new ArrayList<Player>();
        for (Player user : users()) {
            if (user.isPending()) {
                pendingUsers.add(user);
            }
        }
        return pendingUsers;
    }

    public void accept(Player user) {
        get(user).setPending(false);
        persist();
    }

    public void reject(Player user) {
        delete(user);
    }

    public void delete(Player user) {
        users().remove(user);
        persist();
    }

    private Player get(Player user) {
        for (Player u : users()) {
            if (u.equals(user)) {
                return u;
            }
        }
        return null;
    }

    public Player get(String email) {
        return get(new Player(email));
    }

    @Override
    public List<Player> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Player load(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Player find(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Player> findActive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
