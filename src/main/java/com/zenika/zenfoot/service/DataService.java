package com.zenika.zenfoot.service;

import com.zenika.zenfoot.model.Match;

public interface DataService {
    public void registerUser(String email, String password);

    public void updateGoalsForMatch(Match match);
}
