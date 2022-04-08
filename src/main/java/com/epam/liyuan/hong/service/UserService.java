package com.epam.liyuan.hong.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.liyuan.hong.dao.ItemDao;
import com.epam.liyuan.hong.model.User;

@Service
public class UserService {

	private ItemDao itemRepo;

	public Optional<User> getUserById(long userId) {
		return Optional.ofNullable(retrieveUser(u -> u.getId() == userId).get(0));
	}

	public User getUserByEmail(String email) {
		return retrieveUser(u -> u.getEmail().equals(email)).get(0);
	}

	public List<User> getUsersByName(String name, int pageSize, int pageNum) {
		List<User> users = retrieveUser(u -> u.getName().contains(name));
		return getPagedUsers(users, pageSize, pageNum);
	}

	private List<User> retrieveUser(Predicate<User> predicate) {
		return itemRepo.getAllUsers().stream().filter(predicate).collect(Collectors.toList());
	}

	private List<User> getPagedUsers(List<User> events, int pageSize, int pageNum) {
		int firstIndex = (events.size() - 1) / pageSize * pageSize;
		int lastIndex = firstIndex + pageSize > events.size() ? events.size() : firstIndex + pageSize;
		return events.subList(firstIndex, lastIndex);
	}

	public User createUser(User user) {
		itemRepo.saveUser(user);
		return user;
	}

	public User updateUser(User user) {
		if (getUserById(user.getId()).isEmpty()) {
			return null;
		}
		itemRepo.saveUser(user);
		return user;
	}

	public boolean deleteUser(long userId) {
		return itemRepo.deleteUser(userId);
	}

}
