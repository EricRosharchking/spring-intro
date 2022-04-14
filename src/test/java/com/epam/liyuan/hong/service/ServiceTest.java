package com.epam.liyuan.hong.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.model.Ticket.Category;
import com.epam.liyuan.hong.model.User;

@RunWith(SpringRunner.class)
@PropertySource("classpath:app.properties")
@ContextConfiguration("classpath:configuration.xml")
public class ServiceTest {

	@Autowired
	public EventService eventService;
	@Autowired
	public TicketService ticketService;
	@Autowired
	public UserService userService;

	@Test
	public void testGetEventById() {
		assertTrue(eventService.getEventById(1L).isPresent());
		assertTrue(eventService.getEventById(555L).isEmpty());
	}

	@Test
	public void testGetEventsByTitle() {
		assertTrue(eventService.getEventsByTitle("", 1, 1).size() == 1);
		assertTrue(eventService.getEventsByTitle("test", 5, 2).size() > 1);
		assertTrue(eventService.getEventsByTitle("NonExistingEvent", 10, 10).isEmpty());
	}

	@Test
	public void testGetEventsForDay() {
		assertNotEquals(0,
				eventService.getEventsForDay(
						Date.from(LocalDate.of(2022, 04, 13).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10, 10)
						.size());
		assertEquals(0,
				eventService.getEventsForDay(
						Date.from(LocalDate.of(2022, 04, 11).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10, 10)
						.size());
	}

	@Test
	public void testCreateEvent() {
		Event entity = new Event("Unit Test Event", new Date());
		int beforeCreateSize = eventService.getEventsByTitle("Unit Test Event", 10, 10).size();
		assertEquals(eventService.createEvent(entity), entity);
		assertTrue(eventService.getEventsByTitle("Unit Test Event", 10, 10).size() - beforeCreateSize == 1);
	}

	@Test
	public void testUpdateEvent() {
		Event entity = eventService.getEventById(1).get().clone();
		entity.setTitle("Unit Test Event");
		entity.setDate(Date.from(LocalDate.of(2021, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		assertNotEquals(entity, eventService.getEventById(1).get());
		eventService.updateEvent(entity);
		assertEquals(entity, eventService.getEventById(1).get());
	}

	@Test
	public void testDeleteEvent() {
		testCreateEvent();
		Event entity = eventService.getEventsByTitle("Unit Test Event", 10, 10).get(0);
		assertTrue(eventService.deleteEvent(entity.getId()));
		assertFalse(eventService.deleteEvent(entity.getId()));
	}

	@Test
	public void testGetUserById() {
		assertTrue(userService.getUserById(1L).isPresent());
		assertTrue(userService.getUserById(555L).isEmpty());
	}

	@Test
	public void testGetUserByEmail() {
		assertTrue(userService.getUserByEmail("liyuan_hong@epam.com").isPresent());
		assertTrue(userService.getUserByEmail("liyuan_hong@gmail.com").isEmpty());
	}

	@Test
	public void testGetUsersByName() {
		assertEquals(1, userService.getUsersByName("hong", 1, 1).size());
		assertTrue(userService.getUsersByName("liyuan", 5, 2).size() > 1);
		assertTrue(userService.getUsersByName("NonExistingUser", 10, 10).isEmpty());
	}

	@Test
	public void testCreateUser() {
		User entity = new User("Unit Test User", "unit_test_user@example.com");
		int beforeCreateSize = userService.getUsersByName("Unit Test User", 10, 10).size();
		assertEquals(userService.createUser(entity), entity);
		assertTrue(userService.getUsersByName("Unit Test User", 10, 10).size() - beforeCreateSize == 1);
	}

	@Test
	public void testUpdateUser() {
		User entity = userService.getUserById(2).get().clone();
		entity.setName("Unit Test User");
		entity.setEmail("unit_test_user@example.com");
		assertNotEquals(entity, userService.getUserById(2).get());
		userService.updateUser(entity);
		assertEquals(entity, userService.getUserById(2).get());
	}

	@Test
	public void testDeleteUser() {
		User entity = userService.getUserById(2).get();
		assertTrue(userService.deleteUser(entity.getId()));
		assertFalse(userService.deleteUser(entity.getId()));
	}

	@Test(expected = IllegalStateException.class)
	public void testBookTicket() {
		ticketService.bookTicket(0, 0, 3, Category.BAR);
		ticketService.bookTicket(0, 0, 3, Category.BAR);
	}

	@Test
	public void testBookedTicketsByUser() {
		assertEquals(2, ticketService.getBookedTickets(userService.getUserById(1).get(), 10, 10).size());
		assertTrue(ticketService.getBookedTickets(new User("Unit Test User", "unit_test_user@example.com"), 10, 10)
				.isEmpty());
	}

	@Test
	public void testBookedTicketsByEvent() {
		Event e1 = eventService.getEventById(0).get();
		Event e2 = eventService.getEventById(1).get();
		assertEquals(1, ticketService.getBookedTickets(e1, 10, 10).size());
		assertEquals(1, ticketService.getBookedTickets(e2, 10, 10).size());
		Event e3 = new Event("event", new Date());
		System.out.println(e1);
		System.out.println(e2);
		System.out.println(e3);
		assertTrue(ticketService.getBookedTickets(e3, 10, 10).isEmpty());
	}

	@Test
	public void testCancelTicket() {
		Ticket entity = ticketService.bookTicket(999, 999, 999, Category.PREMIUM);
		assertTrue(ticketService.cancelTicket(entity.getId()));
		assertFalse(ticketService.cancelTicket(entity.getId()));
	}

}
