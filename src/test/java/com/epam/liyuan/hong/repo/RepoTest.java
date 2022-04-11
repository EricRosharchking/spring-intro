package com.epam.liyuan.hong.repo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.repo.ItemRepo;

@RunWith(SpringRunner.class)
@PropertySource("classpath:app.properties")
@ContextConfiguration("classpath:configuration.xml")
public class RepoTest {

	@Autowired
	ApplicationContext context;
	
	private ItemRepo itemRepo;

	@Value(".")
	private String classpath;

	@Value("${app.prepareddata.file.event}")
	private String eventsJson;

	@Test
	public void testWrite() {
		Event event = new Event(1L, "test", new Date());
		Map<Long, Event> map = new HashMap<>();
		map.put(event.getId(), event);
		map.put(2L, new Event(2L, "test", new Date()));
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		assertNotNull(itemRepo);
		assertTrue(itemRepo.saveEvents(map));
		try {
			assertTrue(Files.size(Paths.get("", eventsJson)) > 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void testWriteWithAdapter() {
		Event event = new Event(1L, "test", new Date());
		Map<Long, Event> map = new HashMap<>();
		map.put(event.getId(), event);
		map.put(2L, new Event(2L, "test", new Date()));
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		assertNotNull(itemRepo);
		assertTrue(itemRepo.saveEventsWithAdapter(map));
		try {
			assertTrue(Files.size(Paths.get("", eventsJson)) > 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void testRead() {
		itemRepo = (ItemRepo) context.getBean("itemRepo");
		Map<Long, Event> map = new HashMap<>();
		try {
			map = itemRepo.loadEvents();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertNotNull(map.get(1L));
	}
}
