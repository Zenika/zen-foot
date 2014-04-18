package fr.boillodmanuel.restx.gae.services;

import com.google.common.base.Optional;
import fr.boillodmanuel.restx.user.User;
import restx.security.UserService;

public class MockUserService implements UserService<User>{

    private final ZenFootUserRepository zenFootUserRepository;

    public MockUserService(ZenFootUserRepository zenFootUserRepository){
        this.zenFootUserRepository = zenFootUserRepository;
    }

    @Override
    public Optional<User> findUserByName(String name) {
        if(name==null)System.out.println("----------NAME IS NULL----------");
        return zenFootUserRepository.findUserByName(name);
    }

    @Override
    public Optional<User> findAndCheckCredentials(String email, String passwordHash) {

        Optional<User> toRet = zenFootUserRepository.findUserByName(email);

        boolean returns = zenFootUserRepository.findCredentialByUserName(email).get().equals(passwordHash);
        if(returns){
            return toRet;
        }
        else{
            return Optional.absent();
        }
    }
}
