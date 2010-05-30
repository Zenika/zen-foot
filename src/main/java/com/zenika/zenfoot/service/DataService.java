package com.zenika.zenfoot.service;

import com.zenika.zenfoot.model.User;

public interface DataService {
    public void registerUser(String email, String password);

    public void updateUserPoints();

    public void updateUserPoints(User user);
}
