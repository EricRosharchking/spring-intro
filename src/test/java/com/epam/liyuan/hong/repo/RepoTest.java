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

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
//	public void testWrite() {
//		testInitBean();
//		testWriteEvents();
//		testWriteUsers();
//		testWriteTickets();
//	}

//	@Test
//	public void testRead() {
//		testInitBean();
//		testReadEvents();
//		testReadTickets();
//		testReadUsers();
//	}

	@Test
	@Order(1)
	public void testInitBean() {
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		assertNotNull(itemRepo);
	}

	@Test
	@Order(2)
	public void testWriteEvents() throws Exception {
		Event event = new Event("test", new Date());
		Map<Long, Event> map = new HashMap<>();
		map.put(event.getId(), event);
		Event newEvent = new Event("test event", new Date());
		map.put(newEvent.getId(), newEvent);
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		assertTrue(itemRepo.saveEventsToResource(map));
		assertTrue(Files.size(Paths.get("", eventsFile)) > 0);
	}

	@Test
	@Order(3)
	public void testReadEvents() {
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		Map<Long, Event> map = new HashMap<>();
		map = itemRepo.loadEventsFromResource();
		assertEquals(map.size(), 2);
	}

	@Test
	@Order(4)
	public void testWriteUsers() throws IOException {
		Map<Long, User> map = new HashMap<>();
		User u1 = new User("Liyuan", "liyuan@epam.com");
		User u2 = new User("Hong Liyuan", "liyuan_hong@epam.com");
		map.put(u1.getId(), u1);
		map.put(u2.getId(), u2);
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		assertTrue(itemRepo.saveUsersToResource(map));
		assertTrue(Files.size(Paths.get("", usersFile)) > 0);
	}

	@Test
	@Order(5)
	public void testReadUsers() throws IOException {
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		Map<Long, User> map = itemRepo.loadUsersFromResource();
		assertEquals(map.size(), 2);
	}

	@Test
	@Order(6)
	public void testWriteTickets() throws IOException {
		Map<Long, Ticket> map = new HashMap<>();
		Ticket t1 = new Ticket(1L, 1L, Category.BAR, 1);
		Ticket t2 = new Ticket(2L, 2L, Category.PREMIUM, 2);
		map.put(t1.getId(), t1);
		map.put(t2.getId(), t2);
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		assertTrue(itemRepo.saveTicketsToResource(map));
		assertTrue(Files.size(Paths.get("", ticketsFile)) > 0);
	}

	@Test
	@Order(7)
	public void testReadTickets() {
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		Map<Long, Ticket> map = new HashMap<>();
		map = itemRepo.loadTicketsFromResource();
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
