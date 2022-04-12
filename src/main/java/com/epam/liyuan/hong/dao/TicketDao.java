package com.epam.liyuan.hong.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.repo.ItemRepo;

@Component
public class TicketDao {

	private ItemRepo itemRepo;

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

	@PostConstruct
	private void loadTickets() {
		ticketMap = itemRepo.loadTicketsFromResource();
	}

	@PreDestroy
	private void saveAllTickets() {
		itemRepo.saveTicketsToResource(ticketMap);
	}

	@Autowired
	public void setItemRepo(ItemRepo itemRepo) {
		this.itemRepo = itemRepo;
	}

}
