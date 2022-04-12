package com.epam.liyuan.hong.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.repo.ItemRepo;

@Component
public class EventDao {

	private ItemRepo itemRepo;

	private Map<Long, Event> eventMap;

	public void saveEvent(Event event) {
		eventMap.put(event.getId(), event);
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
		eventMap = itemRepo.loadEventsFromResource();
	}
	
	@PreDestroy
	private void saveAllEvents() {
		itemRepo.saveEventsToResource(eventMap);
	}

	@Autowired
	public void setItemRepo(ItemRepo itemRepo) {
		this.itemRepo = itemRepo;
	}

}
