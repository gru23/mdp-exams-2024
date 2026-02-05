package net.etfbl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDAO {
	private static Map<String, UserEntity> users;
	
	static {
		users = new HashMap<String, UserEntity>();
		UserEntity user = new UserEntity("Grujica Vasić", "1");
		users.put(user.getId(), user);
		user = new UserEntity("John Wick", "2");
		users.put(user.getId(), user);
	}
	
	public List<UserEntity> findAll() {
		return List.copyOf(users.values());
	}
	
	public Optional<UserEntity> findById(String id) {
		return Optional.of(users.get(id));
	}
}
