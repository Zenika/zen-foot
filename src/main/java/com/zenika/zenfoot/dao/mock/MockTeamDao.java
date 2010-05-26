package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.TeamDao;
import com.zenika.zenfoot.model.Team;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.User;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MockTeamDao implements TeamDao {
    private List<Team> teams = new ArrayList<Team>();

    public List<Team> find() {
        unser();
        return teams;
    }

    public Team save(Team team) {
        if (teams.contains(team)) {
            //
        } else {
            teams.add(team);
        }
        ser();
        return team;
    }

    private void ser() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("/tmp/zenfoot/teams"));
            out.writeObject(teams);
            out.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " ! CREATE DIRECTORY /tmp/zenfoot MANUALLY for it to work!");
        }
    }

    private void unser() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("/tmp/zenfoot/teams"));
            teams = (List<Team>) in.readObject();
            in.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " ! CREATE DIRECTORY /tmp/zenfoot MANUALLY for it to work!");
        }
    }

    public void delete(Team model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
