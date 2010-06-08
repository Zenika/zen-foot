package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Team;
import com.zenika.zenfoot.model.Player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

public class MockUtil {
    private static final String MOCK_DB_PATH = "zenfoot.ser.donotcommit";
    private static MockDb mockDb;

    static {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(MOCK_DB_PATH));
            mockDb = (MockDb) in.readObject();
            in.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println(MOCK_DB_PATH + " does not exist, attempting to create it!");
            persist();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void persist() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(MOCK_DB_PATH));
            if (mockDb == null) {
                mockDb = MockDb.init();
            }
            out.writeObject(mockDb);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Bet> bets() {
        return mockDb.bets;
    }

    public static List<Match> matchs() {
        return mockDb.matchs;
    }

    public static List<Team> teams() {
        return mockDb.teams;
    }

    public static List<Player> users() {
        return mockDb.users;
    }

    private static class MockDb implements Serializable {
        List<Bet> bets = new ArrayList<Bet>();
        List<Match> matchs = new ArrayList<Match>();
        List<Team> teams = new ArrayList<Team>();
        List<Player> users = new ArrayList<Player>();

        private static MockDb init() throws NoSuchAlgorithmException {
            MockDb mockDb = new MockDb();
            final Player olivier = new Player("olivier@zenika.com");
            olivier.setPassword(DigestUtils.md5Hex("olivier"));
            olivier.setAdmin(true);
            olivier.setPending(false);
            mockDb.users.add(olivier);
            final Player maghen = new Player("maghen@zenika.com");
            maghen.setPassword(DigestUtils.md5Hex("maghen"));
            maghen.setAdmin(true);
            maghen.setPending(false);
            mockDb.users.add(maghen);
            return mockDb;
        }
    }
}
