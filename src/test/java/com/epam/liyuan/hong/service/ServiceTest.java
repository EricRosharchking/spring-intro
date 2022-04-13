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
		assertTrue(!eventService
				.getEventsForDay(Date.from(LocalDate.of(2022, 04, 13).atStartOfDay(ZoneId.systemDefault()).toInstant()),
						10, 10)
				.isEmpty());
		assertTrue(eventService
				.getEventsForDay(Date.from(LocalDate.of(2022, 04, 11).atStartOfDay(ZoneId.systemDefault()).toInstant()),
						10, 10)
				.isEmpty());
	}

	@Test
	public void testCreateEvent() {
		Event entity = new Event(5L, "Unit Test Event", new Date());
		int beforeCreateSize = eventService.getEventsByTitle("Unit Test Event", 10, 10).size();
		assertEquals(eventService.createEvent(entity), entity);
		assertTrue(eventService.getEventsByTitle("Unit Test Event", 10, 10).size() - beforeCreateSize == 1);
	}

	@Test
	public void testUpdateEvent() {
		Event entity = new Event(1L, "Unit Test Event",
				Date.from(LocalDate.of(2021, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		assertNotEquals(entity, eventService.getEventById(1).get());
		assertEquals(entity, eventService.updateEvent(entity));
	}

	@Test
	public void testDeleteEvent() {
		testCreateEvent();
		Event entity = new Event(5L, "Unit Test Event", new Date());
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
		assertTrue(userService.getUsersByName("hong", 1, 1).size() == 1);
		assertTrue(userService.getUsersByName("liyuan", 5, 2).size() > 1);
		assertTrue(userService.getUsersByName("NonExistingUser", 10, 10).isEmpty());
	}

	@Test
	public void testCreateUser() {
		User entity = new User(5L, "Unit Test User", "unit_test_user@example.com");
		int beforeCreateSize = userService.getUsersByName("Unit Test User", 10, 10).size();
		assertEquals(userService.createUser(entity), entity);
		assertTrue(userService.getUsersByName("Unit Test User", 10, 10).size() - beforeCreateSize == 1);
	}

	@Test
	public void testUpdateUser() {
		User entity = new User(userService.getUserById(1).get().getId(), "Unit Test User", "unit_test_user@example.com");
		assertNotEquals(entity, userService.getUserById(1).get());
		assertEquals(entity, userService.updateUser(entity));
	}

	@Test
	public void testDeleteUser() {
		testCreateUser();
		User entity = new User(5L, "Unit Test User", "unit_test_user2@example.com");
		assertTrue(userService.deleteUser(entity.getId()));
		assertFalse(userService.deleteUser(entity.getId()));
	}

}
