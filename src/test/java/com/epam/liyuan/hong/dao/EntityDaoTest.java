package com.epam.liyuan.hong.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.model.Ticket.Category;
import com.epam.liyuan.hong.model.User;

@RunWith(SpringRunner.class)
@PropertySource("classpath:app.properties")
@ContextConfiguration("classpath:configuration.xml")
public class EntityDaoTest {

	@Autowired
	private EventDao eventDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private UserDao userDao;

	// CRUD
	@Test
	public void testCreateEvent() {
		Event event = new Event(999L, "Test Event", new Date());
		assertEquals(event, eventDao.saveEvent(event));
	}

	@Test
	public void testRetrieveEvent() {
		List<Event> list = eventDao.getAllEvents();
		assertTrue(!list.isEmpty());
	}

	@Test
	public void testUpdateEvent() {
		List<Event> list = eventDao.getAllEvents();
		Event event = list.get(0);
		event.setTitle("changed title");
		event.setDate(new Date());
		assertEquals(event, eventDao.saveEvent(event));
		assertEquals(list.size(), eventDao.getAllEvents().size());
	}

	@Test
	public void testDeleteEvent() {
		Event event = eventDao.getAllEvents().get(0);
		assertTrue(eventDao.deleteEvent(event.getId()));
		assertFalse(eventDao.deleteEvent(555L));
	}

	@Test
	public void testCreateUser() {
		User user = new User(999L, "Test User", "test@example.com");
		assertEquals(user, userDao.saveUser(user));
	}

	@Test
	public void testRetrieveUser() {
		List<User> list = userDao.getAllUsers();
		assertTrue(!list.isEmpty());
	}

	@Test
	public void testUpdateUser() {
		List<User> list = userDao.getAllUsers();
		User user = list.get(0);
		user.setName("Changed Name");
		user.setEmail("changed@example.com");
		assertEquals(user, userDao.saveUser(user));
		assertEquals(list.size(), userDao.getAllUsers().size());
	}

	@Test
	public void testDeleteUser() {
		User user = userDao.getAllUsers().get(0);
		assertTrue(userDao.deleteUser(user.getId()));
		assertFalse(userDao.deleteUser(555L));
	}

	@Test
	public void testCreateTicket() {
		Ticket ticket = new Ticket(999L, 999L, Category.BAR, 999);
		assertEquals(ticket, ticketDao.saveTicket(ticket));
	}

	@Test
	public void testRetrieveTicket() {
		List<Ticket> list = ticketDao.getAllTickets();
		assertTrue(!list.isEmpty());
	}

	@Test
	public void testUpdateTicket() {
		List<Ticket> list = ticketDao.getAllTickets();
		Ticket ticket = list.get(0);
		ticket.setEventId(111L);
		ticket.setCategory(Category.STANDARD);
		assertEquals(ticket, ticketDao.saveTicket(ticket));
		assertEquals(list.size(), ticketDao.getAllTickets().size());
	}

	@Test
	public void testDeleteTicket() {
		Ticket ticket = ticketDao.getAllTickets().get(0);
		assertTrue(ticketDao.deleteTicket(ticket.getId()));
		assertFalse(ticketDao.deleteTicket(555L));
	}
}
