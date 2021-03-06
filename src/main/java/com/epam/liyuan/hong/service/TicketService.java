package com.epam.liyuan.hong.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.epam.liyuan.hong.dao.TicketDao;
import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.model.User;

@Service
public class TicketService {

	private TicketDao ticketDao;

	private EventService eventService;

	private UserService userService;

	public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category)
			throws IllegalStateException {
		if (!retrieveTickets(t -> t.getPlace() == place).isEmpty()) {
			throw new IllegalStateException("This place has already been booked");
		}
		Ticket ticket = new Ticket(eventId, userId, category, place);
		return ticketDao.saveTicket(ticket);
	}

	public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
		List<Ticket> tickets = retrieveTickets(t -> t.getUserId() == user.getId());
		if (tickets.size() <= 1) {
			return tickets;
		}
		tickets = tickets.stream()
				.sorted((t1, t2) -> eventService.getEventById(t2.getEventId()).get().getDate()
						.compareTo(eventService.getEventById(t1.getEventId()).get().getDate()))
				.collect(Collectors.toList());
		return getPagedEvents(tickets, pageSize, pageNum);
	}

	public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
		List<Ticket> tickets = retrieveTickets(t -> t.getEventId() == event.getId());
		if (tickets.size() <= 1) {
			return tickets;
		}
		tickets = tickets.stream()
				.sorted((t1, t2) -> userService.getUserById(t1.getUserId()).get().getEmail()
						.compareTo(userService.getUserById(t2.getUserId()).get().getEmail()))
				.collect(Collectors.toList());
		return getPagedEvents(tickets, pageSize, pageNum);
	}

	private List<Ticket> retrieveTickets(Predicate<Ticket> predicate) {
		return ticketDao.getAllTickets().stream().filter(predicate).collect(Collectors.toList());
	}

	private List<Ticket> getPagedEvents(List<Ticket> tickets, int pageSize, int pageNum) {
		if (tickets.isEmpty()) {
			return tickets;
		}
		int firstIndex = (tickets.size() - 1) / pageSize * pageSize;
		int lastIndex = firstIndex + pageSize > tickets.size() ? tickets.size() : firstIndex + pageSize;
		return tickets.subList(firstIndex, lastIndex);
	}

	public boolean cancelTicket(long ticketId) {
//		List<Ticket> ticket = retrieveTickets(t -> t.getId() == ticketId);
//		if (ticket.isEmpty()) {
//			return false;
//		}
		return ticketDao.deleteTicket(ticketId);
	}

	public void setTicketDao(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
