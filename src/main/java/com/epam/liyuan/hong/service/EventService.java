package com.epam.liyuan.hong.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.liyuan.hong.dao.EventDao;
import com.epam.liyuan.hong.model.Event;

@Service
public class EventService {

	private EventDao eventDao;

	public Optional<Event> getEventById(long eventId) {
		return retrieveEvent(e -> eventId == e.getId()).stream().findFirst();
	}

	public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
		List<Event> events = retrieveEvent(e -> e.getTitle().toLowerCase().contains(title.toLowerCase()));
		return getPagedEvents(events, pageSize, pageNum);
	}

	public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
		List<Event> events = retrieveEvent(e -> DateUtils.isSameDay(day, e.getDate()));
		return getPagedEvents(events, pageSize, pageNum);
	}

	private List<Event> retrieveEvent(Predicate<Event> predicate) {
		return eventDao.getAllEvents().stream().filter(predicate).collect(Collectors.toList());
	}

	private List<Event> getPagedEvents(List<Event> events, int pageSize, int pageNum) {
		if (events.isEmpty()) {
			return events;
		}
		int firstIndex = (events.size() - 1) / pageSize * pageSize;
		int lastIndex = firstIndex + pageSize > events.size() ? events.size() : firstIndex + pageSize;
		return events.subList(firstIndex, lastIndex);
	}

	public Event createEvent(Event event) {
		return eventDao.saveEvent(event);
	}

	public Event updateEvent(Event event) throws RuntimeException {
		if (getEventById(event.getId()).isEmpty()) {
			return null;
		}
		return eventDao.saveEvent(event);
	}

	public boolean deleteEvent(long eventId) {
		return eventDao.deleteEvent(eventId);
	}

	public void setEventDao(EventDao eventDao) {
		this.eventDao = eventDao;
	}
	
}
