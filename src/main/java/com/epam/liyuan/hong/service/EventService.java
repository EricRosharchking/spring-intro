package com.epam.liyuan.hong.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.repository.ItemRepo;

@Service
public class EventService {

	@Autowired
	private ItemRepo itemRepo;

	public Optional<Event> getEventById(long eventId) {
		return Optional.ofNullable(retrieveEvent(e -> eventId == e.getId()).get(0));
	}

	public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
		List<Event> events = retrieveEvent(e -> e.getTitle().contains(title));
		return getPagedEvents(events, pageSize, pageNum);
	}

	public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
		List<Event> events = retrieveEvent(e -> day.equals(e.getDate()));
		return getPagedEvents(events, pageSize, pageNum);
	}

	private List<Event> retrieveEvent(Predicate<Event> predicate) {
		return itemRepo.getAllEvents().stream().filter(predicate).collect(Collectors.toList());
	}

	private List<Event> getPagedEvents(List<Event> events, int pageSize, int pageNum) {
		int firstIndex = (events.size() - 1) / pageSize * pageSize;
		int lastIndex = firstIndex + pageSize > events.size() ? events.size() : firstIndex + pageSize;
		return events.subList(firstIndex, lastIndex);
	}

	public Event createEvent(Event event) {
		itemRepo.saveEvent(event);
		return event;
	}

	public Event updateEvent(Event event) throws RuntimeException {
		if (getEventById(event.getId()).isEmpty()) {
			return null;
		}
		itemRepo.saveEvent(event);
		return event;
	}

	public boolean deleteEvent(long eventId) {
		return itemRepo.deleteEvent(eventId);
	}
}
