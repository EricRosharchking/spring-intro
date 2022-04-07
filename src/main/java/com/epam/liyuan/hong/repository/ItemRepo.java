package com.epam.liyuan.hong.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.model.User;

@Component
public class ItemRepo {

	public void saveEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

	public Object getEvent(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteEvent(long eventId) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Event> getAllEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public boolean deleteUser(long userId) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Ticket> getAllTickets() {
		// TODO Auto-generated method stub
		return null;
	}

	public Ticket saveTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteTicket(long ticketId) {
		// TODO Auto-generated method stub
		
	}

}
