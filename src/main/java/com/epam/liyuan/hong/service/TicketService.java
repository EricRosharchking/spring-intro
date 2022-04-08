package com.epam.liyuan.hong.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.liyuan.hong.dao.ItemDao;
import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.model.User;

@Service
public class TicketService {

	private ItemDao itemDao;

	private EventService eventService;

	private UserService userService;

	public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category)
			throws IllegalStateException {
		if (!retrieveTickets(t -> t.getPlace() == place).isEmpty()) {
			throw new IllegalStateException("This place has already been booked");
		}
		Ticket ticket = new Ticket(userId, eventId, category, place);
		return itemDao.saveTicket(ticket);
	}

	public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
		List<Ticket> tickets = retrieveTickets(t -> t.getUserId() == user.getId()).stream()
				.sorted((t1, t2) -> eventService.getEventById(t2.getEventId()).get().getDate()
						.compareTo(eventService.getEventById(t1.getEventId()).get().getDate()))
				.collect(Collectors.toList());
		return getPagedEvents(tickets, pageSize, pageNum);
	}

	public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
		List<Ticket> tickets = retrieveTickets(t -> t.getEventId() == event.getId()).stream()
				.sorted((t1, t2) -> userService.getUserById(t1.getUserId()).get().getEmail()
						.compareTo(userService.getUserById(t2.getUserId()).get().getEmail()))
				.collect(Collectors.toList());
		return getPagedEvents(tickets, pageSize, pageNum);
	}

	private List<Ticket> retrieveTickets(Predicate<Ticket> predicate) {
		return itemDao.getAllTickets().stream().filter(predicate).collect(Collectors.toList());
	}

	private List<Ticket> getPagedEvents(List<Ticket> tickets, int pageSize, int pageNum) {
		int firstIndex = (tickets.size() - 1) / pageSize * pageSize;
		int lastIndex = firstIndex + pageSize > tickets.size() ? tickets.size() : firstIndex + pageSize;
		return tickets.subList(firstIndex, lastIndex);
	}

	public boolean cancelTicket(long ticketId) {
		List<Ticket> ticket = retrieveTickets(t -> t.getId() == ticketId);
		if (ticket.isEmpty()) {
			return false;
		}
		itemDao.deleteTicket(ticketId);
		return true;
	}

}
