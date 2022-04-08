package com.epam.liyuan.hong.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.model.User;

@Component
public class UserDao {

	private Map<Long, User> userMap;

	public void saveUser(User user) {
		userMap.put(user.getId(), user);
	}

	public List<User> getAllUsers() {
		return new ArrayList<User>(userMap.values());
	}

	public boolean deleteUser(long userId) {
		if (!userMap.containsKey(userId)) {
			return false;
		}
		userMap.remove(userId);
		return true;
	}

}
