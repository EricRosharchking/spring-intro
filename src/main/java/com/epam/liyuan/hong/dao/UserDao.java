package com.epam.liyuan.hong.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.liyuan.hong.model.User;
import com.epam.liyuan.hong.repo.ItemRepo;

@Component
public class UserDao {

	private final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	private ItemRepo itemRepo;

	private final Map<Long, User> userMap = new HashMap<>();;

	public User saveUser(User user) {
		userMap.put(user.getId(), user);
		return user;
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

	@PostConstruct
	private void loadAllUsers() {
		userMap.putAll(itemRepo.loadUsersFromResource());
		userMap.keySet().stream().forEach(key -> logger.info("User Key: " + key));
	}

	@PreDestroy
	private void saveAllUsers() {
		itemRepo.saveUsersToResource(userMap);
	}

	@Autowired
	public void setItemRepo(ItemRepo itemRepo) {
		this.itemRepo = itemRepo;
	}

}
