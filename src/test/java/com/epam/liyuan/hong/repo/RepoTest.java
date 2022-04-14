package com.epam.liyuan.hong.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepoTest {

	@Autowired
	ApplicationContext context;

	private ItemRepo itemRepo;

	@Value(".")
	private String classpath;

	@Value("${app.prepareddata.file.event}")
	private String eventsFile;

	@Value("${app.prepareddata.file.user}")
	private String usersFile;

	@Value("${app.prepareddata.file.ticket}")
	private String ticketsFile;

//	@Test
	public void testWrite() {
		testInitBean();
		testWriteEvents();
		testWriteUsers();
		testWriteTickets();
	}
	
//	@Test
	public void testRead() {
		testInitBean();
		testReadEvents();
		testReadTickets();
		testReadUsers();
	}
	
	@Test
	public void testInitBean() {
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		assertNotNull(itemRepo);
	}

	@Test
	public void testWriteEvents() {
		Event event = new Event("test", new Date());
		Map<Long, Event> map = new HashMap<>();
		map.put(event.getId(), event);
		Event newEvent = new Event("test event", new Date());
		map.put(newEvent.getId(), newEvent);
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		assertTrue(itemRepo.saveEventsToResource(map));
		try {
			assertTrue(Files.size(Paths.get("", eventsFile)) > 0);
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
//		testReadEvents();
	}

	@Test
	public void testReadEvents() {
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		Map<Long, Event> map = new HashMap<>();
		try {
			map = itemRepo.loadEventsFromResource();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertEquals(map.size(), 2);
	}

	@Test
	public void testWriteUsers() {
		Map<Long, User> map = new HashMap<>();
		User u1 = new User("Liyuan", "liyuan@epam.com");
		User u2 = new User("Hong Liyuan", "liyuan_hong@epam.com");
		map.put(u1.getId(), u1);
		map.put(u2.getId(), u2);
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		assertTrue(itemRepo.saveUsersToResource(map));
		try {
			assertTrue(Files.size(Paths.get("", usersFile)) > 0);
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
//		testReadUsers();
	}

	@Test
	public void testReadUsers() {
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		Map<Long, User> map = new HashMap<>();
		try {
			map = itemRepo.loadUsersFromResource();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertEquals(map.size(), 2);
	}

	@Test
	public void testWriteTickets() {
		Map<Long, Ticket> map = new HashMap<>();
		Ticket t1 = new Ticket(1L, 1L, Category.BAR, 1);
		Ticket t2 = new Ticket(2L, 2L, Category.PREMIUM, 2);
		map.put(t1.getId(), t1);
		map.put(t2.getId(), t2);
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		assertTrue(itemRepo.saveTicketsToResource(map));
		try {
			assertTrue(Files.size(Paths.get("", ticketsFile)) > 0);
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testReadTickets() {
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		Map<Long, Ticket> map = new HashMap<>();
		try {
			map = itemRepo.loadTicketsFromResource();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertEquals(map.size(), 2);
	}

	/*
	 * public void testWriteWithAdapter() { Event event = new Event(1L, "test", new
	 * Date()); Map<Long, Event> map = new HashMap<>(); map.put(event.getId(),
	 * event); map.put(2L, new Event(2L, "test", new Date())); itemRepo = (ItemRepo)
	 * context.getBean("itemRepo"); assertNotNull(itemRepo);
	 * assertTrue(itemRepo.saveEventsWithAdapter(map)); try {
	 * assertTrue(Files.size(Paths.get("", eventsFile)) > 0); } catch (IOException
	 * e) { // TODO Auto-generated catch block e.printStackTrace();
	 * assertTrue(false); } }
	 * 
	 * public void tesReadWithAdapter() { itemRepo = (ItemRepo)
	 * context.getBean("itemRepo"); Map<Long, Event> map = new HashMap<>(); try {
	 * map = itemRepo.loadEventsWithAdapter(); } catch (Exception e) {
	 * e.printStackTrace(); assertTrue(false); } assertNotNull(map.get(1L)); }
	 */
}
