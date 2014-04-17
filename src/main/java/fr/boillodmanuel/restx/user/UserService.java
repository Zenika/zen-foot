package fr.boillodmanuel.restx.user;

import java.util.HashMap;

import restx.factory.Component;

import com.google.common.base.Optional;

@Component
public class UserService {

	public Optional<User> findUserByName(String name) {
		HashMap<String, User> users = new HashMap<>();
		users.put("Raphael", new User("Raphael"));
		Optional<User> toRet = Optional.absent();

		User user = users.get(name);
		if (user != null) {
			toRet = Optional.of(user);
		}

		return toRet;

	}

}
