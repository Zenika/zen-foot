package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.model.Bet;
import com.zenika.zenfoot.user.User;
import restx.factory.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by raphael on 24/04/14.
 */

@Component
public class BetRepository {


    private HashMap<User, List<Bet>> userBet;

    public BetRepository() {

        userBet = new HashMap<>();

    }

    public List<Bet> getBets(User user) {
        return userBet.get(user);
    }

    public void addBet(User user, Bet bet) {

        for (int i = 0; i < 10; i++) {
            System.out.println("ICICICICICICI");
        }
        List<Bet> bets = this.userBet.get(user);
        if (bets == null) {
            bets = new ArrayList<>();
            bets.add(bet);
            this.userBet.put(user, bets);

        } else {
            bets.add(bet);
            this.userBet.put(user, bets);
        }
    }
}
