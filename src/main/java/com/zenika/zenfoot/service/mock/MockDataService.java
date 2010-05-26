package com.zenika.zenfoot.service.mock;

import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.dao.mock.MockUserDao;
import com.zenika.zenfoot.model.User;
import com.zenika.zenfoot.service.DataService;

public class MockDataService implements DataService {
    private transient UserDao userDao = new MockUserDao();

    public void registerUser(String email, String password) {
        userDao.save(new User(email));
    }
}
