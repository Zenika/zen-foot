package com.zenika.zenfoot.gae;

import com.zenika.zenfoot.gae.dao.UserDAOImpl;
import com.zenika.zenfoot.gae.dao.UserDao;
import restx.factory.Module;
import restx.factory.Provides;

import javax.inject.Named;

/**
 * Created by raphael on 23/04/14.
 */

@Module
public class DaoModule {

    @Provides
    @Named("userdao")
    public UserDao getUserDao(){
       return new UserDAOImpl();
    }
}
