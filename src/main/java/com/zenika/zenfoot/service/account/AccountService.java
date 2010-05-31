package com.zenika.zenfoot.service.account;

import com.zenika.zenfoot.model.User;

public interface AccountService {
    public void register(String email, String password);

    public void sendPassword(String email);

    public void accept(User user);

    public void reject(User user);

    public void setAdminEmail(String adminEmail);

    public void setAppUrl(String appUrl);
}
