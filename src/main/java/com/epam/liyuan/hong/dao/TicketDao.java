package com.epam.liyuan.hong.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.repo.ItemRepo;

@Component
public class TicketDao {
	
	private final Logger logger = LoggerFactory.getLogger(TicketDao.class);

	private ItemRepo itemRepo;

	private final Map<Long, Ticket> ticketMap = new HashMap<>();

	public Ticket saveTicket(Ticket ticket) {
		ticketMap.put(ticket.getId(), ticket);
		return ticket;
	}

	public List<Ticket> getAllTickets() {
		return new ArrayList<Ticket>(ticketMap.values());
	}

	public boolean deleteTicket(long ticketId) {
		if (!ticketMap.containsKey(ticketId)) {
			ticketMap.keySet().stream().forEach(key -> logger.info("key:" + key));
			return false;
		}
		ticketMap.remove(ticketId);
		return true;
	}

	@PostConstruct
	private void loadTickets() {
		ticketMap.putAll(itemRepo.loadTicketsFromResource());
	}

//	@PreDestroy
	private void saveAllTickets() {
		itemRepo.saveTicketsToResource(ticketMap);
	}

	@Autowired
	public void setItemRepo(ItemRepo itemRepo) {
		this.itemRepo = itemRepo;
	}

}
