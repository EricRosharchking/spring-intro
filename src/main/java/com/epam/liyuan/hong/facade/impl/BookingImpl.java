package com.epam.liyuan.hong.facade.impl;

import java.util.Date;
import java.util.List;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event createEvent(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event updateEvent(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteEvent(long eventId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUserById(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersByName(String name, int pageSize, int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUser(long userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Ticket bookTicket(long userId, long eventId, int place, Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean cancelTicket(long ticketId) {
		// TODO Auto-generated method stub
		return false;
	}

}
