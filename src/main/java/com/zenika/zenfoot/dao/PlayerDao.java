package com.zenika.zenfoot.dao;

import com.zenika.zenfoot.model.Player;
import java.util.List;

public interface PlayerDao extends BaseDao<Player> {
    public List<Player> findActive();

    public List<Player> findPending();

    public void accept(Player user);

    public void reject(Player user);

    public Player find(String email);
}
