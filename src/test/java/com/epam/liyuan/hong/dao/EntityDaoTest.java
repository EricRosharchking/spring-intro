package com.epam.liyuan.hong.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.model.Ticket.Category;
import com.epam.liyuan.hong.model.User;

@ExtendWith(SpringExtension.class)
@PropertySource("classpath:app.properties")
@ContextConfiguration("classpath:configuration.xml")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntityDaoTest {

	@Autowired
	private EventDao eventDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private UserDao userDao;

	// CRUD
	@Test
	@Order(1)
	public void testCreateEvent() {
		Event event = new Event("Test Event", new Date());
		assertEquals(event, eventDao.saveEvent(event));
	}

	@Test
	@Order(2)
	public void testRetrieveEvent() {
		List<Event> list = eventDao.getAllEvents();
		assertTrue(!list.isEmpty());
	}

	@Test
	@Order(3)
	public void testUpdateEvent() {
		List<Event> list = eventDao.getAllEvents();
		Event event = list.get(0);
		event.setTitle("changed title");
		event.setDate(new Date());
		assertEquals(event, eventDao.saveEvent(event));
		assertEquals(list.size(), eventDao.getAllEvents().size());
	}

	@Test
	@Order(4)
	public void testDeleteEvent() {
		Event event = eventDao.getAllEvents().get(0);
		assertTrue(eventDao.deleteEvent(event.getId()));
		assertFalse(eventDao.deleteEvent(555L));
	}

	@Test
	@Order(5)
	public void testCreateUser() {
		User user = new User("Test User", "test@example.com");
		assertEquals(user, userDao.saveUser(user));
	}

	@Test
	@Order(6)
	public void testRetrieveUser() {
		List<User> list = userDao.getAllUsers();
		assertTrue(!list.isEmpty());
	}

	@Test
	@Order(7)
	public void testUpdateUser() {
		List<User> list = userDao.getAllUsers();
		User user = list.get(0);
		user.setName("Changed Name");
		user.setEmail("changed@example.com");
		assertEquals(user, userDao.saveUser(user));
		assertEquals(list.size(), userDao.getAllUsers().size());
	}

	@Test
	@Order(8)
	public void testDeleteUser() {
		User user = userDao.getAllUsers().get(0);
		assertTrue(userDao.deleteUser(user.getId()));
		assertFalse(userDao.deleteUser(555L));
	}

	@Test
	@Order(9)
	public void testCreateTicket() {
		Ticket ticket = new Ticket(999L, 999L, Category.BAR, 999);
		assertEquals(ticket, ticketDao.saveTicket(ticket));
	}

	@Test
	@Order(10)
	public void testRetrieveTicket() {
		List<Ticket> list = ticketDao.getAllTickets();
		assertTrue(!list.isEmpty());
	}

	@Test
	@Order(11)
	public void testUpdateTicket() {
		List<Ticket> list = ticketDao.getAllTickets();
		Ticket ticket = list.get(0);
		ticket.setEventId(111L);
		ticket.setCategory(Category.STANDARD);
		assertEquals(ticket, ticketDao.saveTicket(ticket));
		assertEquals(list.size(), ticketDao.getAllTickets().size());
	}

	@Test
	@Order(12)
	public void testDeleteTicket() {
		Ticket ticket = ticketDao.getAllTickets().get(0);
		assertTrue(ticketDao.deleteTicket(ticket.getId()));
		assertFalse(ticketDao.deleteTicket(555L));
	}
}
