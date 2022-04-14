package com.epam.liyuan.hong.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.liyuan.hong.dao.UserDao;
import com.epam.liyuan.hong.model.User;

@Service
public class UserService {

	private UserDao userDao;

	public Optional<User> getUserById(long userId) {
		return retrieveUser(u -> u.getId() == userId).stream().findFirst();
	}

	public Optional<User> getUserByEmail(String email) {
		return retrieveUser(u -> u.getEmail().equals(email.toLowerCase())).stream().findFirst();
	}

	public List<User> getUsersByName(String name, int pageSize, int pageNum) {
		List<User> users = retrieveUser(u -> u.getName().toLowerCase().contains(name.toLowerCase()));
		return getPagedUsers(users, pageSize, pageNum);
	}

	private List<User> retrieveUser(Predicate<User> predicate) {
		return userDao.getAllUsers().stream().filter(predicate).collect(Collectors.toList());
	}

	private List<User> getPagedUsers(List<User> events, int pageSize, int pageNum) {
		if (events.isEmpty()) {
			return events;
		}
		int firstIndex = (events.size() - 1) / pageSize * pageSize;
		int lastIndex = firstIndex + pageSize > events.size() ? events.size() : firstIndex + pageSize;
		return events.subList(firstIndex, lastIndex);
	}

	public User createUser(User user) {
		userDao.saveUser(user);
		return user;
	}

	public User updateUser(User user) {
		if (getUserById(user.getId()).isEmpty()) {
			return null;
		}
		userDao.saveUser(user);
		return user;
	}

	public boolean deleteUser(long userId) {
		return userDao.deleteUser(userId);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
