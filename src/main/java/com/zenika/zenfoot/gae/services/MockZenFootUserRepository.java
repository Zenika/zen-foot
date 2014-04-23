package com.zenika.zenfoot.gae.services;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.user.User;
import org.joda.time.DateTime;
import restx.admin.AdminModule;
import restx.factory.Component;

import java.util.*;

@Component
public class MockZenFootUserRepository implements ZenFootUserRepository {

	private Map<String, Optional<User>> users;
   // private UserDao userDao;

	public MockZenFootUserRepository() {
		this.users = new HashMap<>();
      //  this.userDao = Factory.getInstance().getComponent(UserDao.class);

        User raphael = new User().setName("raphael").setEmail(
                "raphael.martignoni@zenika.com").setRoles(Arrays.asList(Roles.ADMIN,Roles.GAMBLER, AdminModule.RESTX_ADMIN_ROLE));

        raphael.setLastUpdated(DateTime.now());
        raphael.setPasswordHash("2205");
        Optional<User> userOpt = Optional.of(raphael);

     //   this.userDao.addUser(raphael);
        users.put(raphael.getEmail(), userOpt);


	}

    @Override
    public Optional<User> findUserByName(String email) {

        if(email!=null) {
            Optional<User> userOptional = this.users.get(email);
            if (userOptional != null) {
                return userOptional;
            }
            return Optional.absent();
        }
        else{
            return Optional.absent();
        }
    }

    @Override
    public Optional<String> findCredentialByUserName(String email) {
        System.out.println("------------EMAIL de la personne cherchant à s'ID----------");
        System.out.println(email);

        Optional<User> user = findUserByName(email);
        System.out.println("---------------L'utilisateur est présent : ");
        System.out.println(user.isPresent());
        if (!user.isPresent()) {
            return Optional.absent();
        } else {
            return Optional.fromNullable(user.get().getPasswordHash());
        }
    }

    @Override
    public boolean isAdminDefined() {
        return false;
    }

    @Override
    public User defaultAdmin() {
        return this.users.get("raphael.martignoni@zenika.com").get();
    }

    // ///////////////////////////////////////////////////////
    // repo update methods
    // ///////////////////////////////////////////////////////
    public User createUser(User user) {

        this.users.put(user.getEmail(), Optional.fromNullable(user));
        return user;
    }

    public User updateUser(User user) {
        this.users.put(user.getEmail(), Optional.fromNullable(user));
        return user;
    }

    public Iterable<User> findAllUsers() {
        ArrayList<User> iterable = new ArrayList<>();
        for (Iterator<String> it = this.users.keySet().iterator(); it.hasNext();) {

            Optional<User> opt = this.users.get(it.next());
            if (opt.isPresent()) {
                iterable.add(opt.get());
            }
        }
        return iterable;
    }

    public void deleteUser(String userRef) {
        this.users.remove(userRef);
    }



}
