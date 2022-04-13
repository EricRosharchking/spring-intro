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
		Event event = new Event(5L, "Unit Test Event", new Date());
		int beforeCreateSize = eventService.getEventsByTitle("Unit Test Event", 10, 10).size();
		assertEquals(eventService.createEvent(event), event);
		assertTrue(eventService.getEventsByTitle("Unit Test Event", 10, 10).size() - beforeCreateSize == 1);
	}

	@Test
	public void testUpdateEvent() {
		Event event = new Event(1L, "Unit Test Event",
				Date.from(LocalDate.of(2021, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		System.out.println(event);
		System.out.println(eventService.getEventById(1).get());
		assertNotEquals(event, eventService.getEventById(1).get());
		assertEquals(event, eventService.updateEvent(event));
	}

	@Test
	public void testDeleteEvent() {
		testCreateEvent();
		Event event = new Event(5L, "Unit Test Event", new Date());
		assertTrue(eventService.deleteEvent(event.getId()));
		assertFalse(eventService.deleteEvent(event.getId()));
	}

}
