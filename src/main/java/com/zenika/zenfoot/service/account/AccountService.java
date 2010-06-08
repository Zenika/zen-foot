package com.zenika.zenfoot.service.account;

import com.zenika.zenfoot.model.Player;

public interface AccountService {
    public void register(String email, String password);

    public void sendPassword(String email);

    public void accept(Player user);

    public void reject(Player user);

    public void setAdminEmail(String adminEmail);

    public void setAppUrl(String appUrl);
}
