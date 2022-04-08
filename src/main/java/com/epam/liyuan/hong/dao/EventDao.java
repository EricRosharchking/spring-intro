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
public class EventDao {

	private Map<Long, Event> eventMap;

	public void saveEvent(Event event) {
		eventMap.put(event.getId(), event);
	}

	public List<Event> getAllEvents() {
		return new ArrayList<Event>(eventMap.values());
	}

//	public Event getEvent(Event event) {
//		return eventMap.get(event.getId());
//	}

	public boolean deleteEvent(long eventId) {
		// TODO Auto-generated method stub
		return false;
	}
}
