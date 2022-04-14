package com.epam.liyuan.hong.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
public class ServiceTest {

	@Autowired
	public EventService eventService;
	@Autowired
	public TicketService ticketService;
	@Autowired
	public UserService userService;

	@Test
	@Order(1)
	public void testGetEventById() {
		assertTrue(eventService.getEventById(1L).isPresent());
		assertTrue(eventService.getEventById(555L).isEmpty());
	}

	@Test
	@Order(2)
	public void testGetEventsByTitle() {
		assertTrue(eventService.getEventsByTitle("", 1, 1).size() == 1);
		assertTrue(eventService.getEventsByTitle("test", 5, 2).size() > 1);
		assertTrue(eventService.getEventsByTitle("NonExistingEvent", 10, 10).isEmpty());
	}

	@Test
	@Order(3)
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
	@Order(4)
	public void testCreateEvent() {
		Event entity = new Event("Unit Test Event", new Date());
		int beforeCreateSize = eventService.getEventsByTitle("Unit Test Event", 10, 10).size();
		assertEquals(eventService.createEvent(entity), entity);
		assertTrue(eventService.getEventsByTitle("Unit Test Event", 10, 10).size() - beforeCreateSize == 1);
	}

	@Test
	@Order(5)
	public void testUpdateEvent() {
		Event entity = eventService.getEventById(1).get().clone();
		entity.setTitle("Update Test Event");
		entity.setDate(Date.from(LocalDate.of(2021, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		assertNotEquals(entity, eventService.getEventById(1).get());
		eventService.updateEvent(entity);
		assertEquals(entity, eventService.getEventById(1).get());
	}

	@Test
	@Order(6)
	public void testDeleteEvent() {
		Event entity = eventService.getEventsByTitle("Unit Test Event", 10, 10).get(0);
		assertTrue(eventService.deleteEvent(entity.getId()));
		assertFalse(eventService.deleteEvent(entity.getId()));
	}

	@Test
	@Order(7)
	public void testGetUserById() {
		assertTrue(userService.getUserById(1L).isPresent());
		assertTrue(userService.getUserById(555L).isEmpty());
	}

	@Test
	@Order(8)
	public void testGetUserByEmail() {
		assertTrue(userService.getUserByEmail("liyuan_hong@epam.com").isPresent());
		assertTrue(userService.getUserByEmail("liyuan_hong@gmail.com").isEmpty());
	}

	@Test
	@Order(9)
	public void testGetUsersByName() {
		assertEquals(1, userService.getUsersByName("hong", 1, 1).size());
		assertEquals(3, userService.getUsersByName("", 5, 2).size());
		assertTrue(userService.getUsersByName("NonExistingUser", 10, 10).isEmpty());
	}

	@Test
	@Order(10)
	public void testCreateUser() {
		User entity = new User("Unit Test User", "unit_test_user@example.com");
		int beforeCreateSize = userService.getUsersByName("Unit Test User", 10, 10).size();
		assertEquals(userService.createUser(entity), entity);
		assertTrue(userService.getUsersByName("Unit Test User", 10, 10).size() - beforeCreateSize == 1);
	}

	@Test
	@Order(11)
	public void testUpdateUser() {
		User user = new User("Tom Hanks", "hanks_tom@gmail.com");
		long id = user.getId();
		userService.createUser(user);
		User entity = user.clone();
		entity.setName("Unit Test User");
		entity.setEmail("unit_test_user@example.com");
		assertNotEquals(entity, userService.getUserById(id).get());
		userService.updateUser(entity);
		assertEquals(entity, userService.getUserById(id).get());
	}

	@Test
	@Order(12)
	public void testDeleteUser() {
		User entity = userService.getUserByEmail("unit_test_user@example.com").get();
		assertTrue(userService.deleteUser(entity.getId()));
		assertFalse(userService.deleteUser(entity.getId()));
	}

	@Test
	@Order(13)
	public void testBookTicket() {
		User user = userService.getUserById(5).get();
		Event event = eventService.getEventById(1).get();
		int place = (int) (user.getId() + event.getId());
		assertNotNull(ticketService.bookTicket(user.getId(), event.getId(), place, Category.BAR));
		assertThrowsExactly(IllegalStateException.class, () -> {
			ticketService.bookTicket(user.getId(), event.getId(), place, Category.BAR);
		});
	}

	@Test
	@Order(14)
	public void testBookedTicketsByUser() {
		User user = userService.getUserById(1).get();
		assertEquals(2, ticketService.getBookedTickets(user, 10, 10).size());
		assertTrue(ticketService.getBookedTickets(new User("Unit Test User", "unit_test_user@example.com"), 10, 10)
				.isEmpty());
	}

	@Test
	@Order(15)
	public void testBookedTicketsByEvent() {
		Event e1 = eventService.getEventById(0).get();
		Event e2 = eventService.getEventById(1).get();
		assertEquals(1, ticketService.getBookedTickets(e1, 10, 10).size());
		assertEquals(2, ticketService.getBookedTickets(e2, 10, 10).size());
		Event e3 = new Event("event", new Date());
		assertTrue(ticketService.getBookedTickets(e3, 10, 10).isEmpty());
	}

	@Test
	@Order(16)
	public void testCancelTicket() {
		Ticket entity = ticketService.bookTicket(999, 999, 999, Category.PREMIUM);
		assertTrue(ticketService.cancelTicket(entity.getId()));
		assertFalse(ticketService.cancelTicket(entity.getId()));
	}

}
