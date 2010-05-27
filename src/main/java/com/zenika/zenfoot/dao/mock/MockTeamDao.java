package com.zenika.zenfoot.dao.mock;

import com.zenika.zenfoot.dao.TeamDao;
import com.zenika.zenfoot.model.Team;
import java.util.List;
import static com.zenika.zenfoot.dao.mock.MockUtil.teams;
import static com.zenika.zenfoot.dao.mock.MockUtil.persist;

public class MockTeamDao implements TeamDao {
    public List<Team> find() {
        return teams();
    }

    public Team save(Team team) {
        if (!teams().contains(team)) {
            teams().add(team);
        }
        persist();
        return team;
    }

    public void delete(Team model) {
        teams().remove(model);
        persist();
    }
}
