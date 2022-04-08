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
public class TicketDao {
	
	private Map<Long, Ticket> ticketMap;

	public void saveTicket(Ticket ticket) {
		ticketMap.put(ticket.getId(), ticket);
	}

	public List<Ticket> getAllTickets() {
		return new ArrayList<Ticket>(ticketMap.values());
	}

	public boolean deleteTicket(long ticketId) {
		if (!ticketMap.containsKey(ticketId)) {
			return false;
		}
		ticketMap.remove(ticketId);
		return true;
	}

}
