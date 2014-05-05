package com.zenika.zenfoot.gae.dao;

import com.zenika.zenfoot.gae.model.Gambler;

import java.util.List;

/**
 * Created by raphael on 30/04/14.
 */
public interface GamblerDAO {

    void saveGambler(Gambler gambler);

    Gambler getGambler(Long id);

    void deleteGambler(Long id);

    List<Gambler> getAll();

    //TODO delete
    void deleteAll();

    Gambler getGamblerFromEmail(String email);

}
