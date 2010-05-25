package com.zenika.zenfoot.service.mock;

import com.zenika.zenfoot.service.MailService;

public class MockMailService implements MailService {
    public void sendPassword(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
