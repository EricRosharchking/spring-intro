package com.epam.liyuan.hong.facade.impl;

import java.util.Date;
import java.util.List;

import com.epam.liyuan.hong.exception.EntityNotFoundException;
import com.epam.liyuan.hong.facade.BookingFacade;
import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.model.Ticket.Category;
import com.epam.liyuan.hong.model.User;
import com.epam.liyuan.hong.service.EventService;
import com.epam.liyuan.hong.service.TicketService;
import com.epam.liyuan.hong.service.UserService;

public class BookingImpl implements BookingFacade {

	private EventService eventService;
	private TicketService ticketService;
	private UserService userService;

	public BookingImpl(EventService eventService, TicketService ticketService, UserService userService) {
		super();
		this.eventService = eventService;
		this.ticketService = ticketService;
		this.userService = userService;
	}

	@Override
	public Event getEventById(long eventId) {
		return eventService.getEventById(eventId).orElseThrow(() -> supplyException());
	}

	@Override
	public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
		return eventService.getEventsByTitle(title, pageSize, pageNum);
	}

	@Override
	public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
		return eventService.getEventsForDay(day, pageSize, pageNum);
	}

	@Override
	public Event createEvent(Event event) {
		return eventService.createEvent(event);
	}

	@Override
	public Event updateEvent(Event event) {
		return eventService.updateEvent(event);
	}

	@Override
	public boolean deleteEvent(long eventId) {
		return eventService.deleteEvent(eventId);
	}

	@Override
	public User getUserById(long userId) {
		return userService.getUserById(userId).orElseThrow(() -> supplyException());
	}

	@Override
	public User getUserByEmail(String email) {
		return userService.getUserByEmail(email).orElseThrow(() -> supplyException());
	}

	@Override
	public List<User> getUsersByName(String name, int pageSize, int pageNum) {
		return userService.getUsersByName(name, pageSize, pageNum);
	}

	@Override
	public User createUser(User user) {
		return userService.createUser(user);
	}

	@Override
	public User updateUser(User user) {
		return userService.updateUser(user);
	}

	@Override
	public boolean deleteUser(long userId) {
		return userService.deleteUser(userId);
	}

	@Override
	public Ticket bookTicket(long userId, long eventId, int place, Category category) {
		return ticketService.bookTicket(userId, eventId, place, category);
	}

	@Override
	public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
		return ticketService.getBookedTickets(user, pageSize, pageNum);
	}

	@Override
	public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
		return ticketService.getBookedTickets(event, pageSize, pageNum);
	}

	@Override
	public boolean cancelTicket(long ticketId) {
		return ticketService.cancelTicket(ticketId);
	}
	
	private EntityNotFoundException supplyException() {
		return new EntityNotFoundException();
	}

}
