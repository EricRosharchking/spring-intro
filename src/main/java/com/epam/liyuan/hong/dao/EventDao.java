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

import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.repo.ItemRepo;

@Component
public class EventDao {

	private final Logger logger = LoggerFactory.getLogger(EventDao.class);

	@Autowired
	private ItemRepo itemRepo;

	private final Map<Long, Event> eventMap = new HashMap<>();;

	public Event saveEvent(Event event) {
		eventMap.put(event.getId(), event);
		return event;
	}

	public List<Event> getAllEvents() {
		return new ArrayList<Event>(eventMap.values());
	}

	public boolean deleteEvent(long eventId) {
		if (!eventMap.containsKey(eventId)) {
			return false;
		}
		eventMap.remove(eventId);
		return true;
	}

	public ItemRepo getItemRepo() {
		return itemRepo;
	}

	@PostConstruct
	private void loadEvents() {
		eventMap.putAll(itemRepo.loadEventsFromResource());
		eventMap.keySet().stream().forEach(k -> logger.info("Event Key: " + k));
	}

//	@PreDestroy
	private void saveAllEvents() {
		itemRepo.saveEventsToResource(eventMap);
	}

	@Autowired
	public void setItemRepo(ItemRepo itemRepo) {
		this.itemRepo = itemRepo;
	}

}
