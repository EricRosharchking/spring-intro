package com.epam.liyuan.hong.repo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.model.User;
import com.epam.liyuan.hong.util.EventTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

@Component
public class ItemRepo {

//	@Autowired
//	@Qualifier(value = "eventFileResource")
	private FileSystemResource savedEventsResource;
//	@Autowired
//	@Qualifier(value = "ticketFileResource")
	private FileSystemResource savedTicketsResource;
//	@Autowired
//	@Qualifier(value = "userFileResource")
	private FileSystemResource savedUsersResource;

	public Map<Long, Event> loadEventsFromResource() {
		Map<String, Object> tempMap = new HashMap<>();
		try {
			tempMap.putAll(readTempMapFromResource(Event.class, savedEventsResource));
		} catch (NullPointerException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}
		int prefixLength = getPrefix(Event.class).length();
		Map<Long, Event> resMap = new HashMap<>();
		if (!tempMap.isEmpty()) {
			tempMap.entrySet().stream().forEach(entry -> {
//				System.out.println(entry);
				resMap.put(Long.valueOf(entry.getKey().substring(prefixLength)), (Event) entry.getValue());
			});
		}
		Event.setSequence(resMap.size());
		return resMap;
	}

	public boolean saveEventsToResource(Map<Long, Event> eventsMap) {
		JsonElement element = createJsonElementMap(Event.class, eventsMap);
		try {
			writeJsonObjectToResource(element, savedEventsResource);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Map<Long, User> loadUsersFromResource() {
		Map<String, Object> tempMap = new HashMap<>();
		try {
			tempMap.putAll(readTempMapFromResource(User.class, savedUsersResource));
		} catch (NullPointerException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}
		int prefixLength = getPrefix(User.class).length();
		Map<Long, User> resMap = new HashMap<>();
		if (!tempMap.isEmpty()) {
			tempMap.entrySet().stream().forEach(entry -> {
//				System.out.println(entry);
				resMap.put(Long.valueOf(entry.getKey().substring(prefixLength)), (User) entry.getValue());
			});
		}
		User.setSequence(resMap.size());
		return resMap;
	}

	public boolean saveUsersToResource(Map<Long, User> usersMap) {
		JsonElement element = createJsonElementMap(User.class, usersMap);
		try {
			writeJsonObjectToResource(element, savedUsersResource);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Map<Long, Ticket> loadTicketsFromResource() {
		Map<String, Object> tempMap = new HashMap<>();
		try {
			tempMap.putAll(readTempMapFromResource(Ticket.class, savedTicketsResource));
		} catch (NullPointerException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}
		int prefixLength = getPrefix(Ticket.class).length();
		Map<Long, Ticket> resMap = new HashMap<>();
		if (!tempMap.isEmpty()) {
			tempMap.entrySet().stream().forEach(entry -> {
//				System.out.println(entry);
				resMap.put(Long.valueOf(entry.getKey().substring(prefixLength)), (Ticket) entry.getValue());
			});
		}
		Ticket.setSequence(resMap.size());
		return resMap;
	}

	public boolean saveTicketsToResource(Map<Long, Ticket> ticketsMap) {
		JsonElement element = createJsonElementMap(Ticket.class, ticketsMap);
		try {
			writeJsonObjectToResource(element, savedTicketsResource);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private Map<String, Object> readTempMapFromResource(Class<? extends Object> entityClass, Resource resource)
			throws Exception {
		JsonElement element = JsonParser.parseReader(readJsonObjectFromResource(resource));
		Gson gson = getGson();
		Type type = getMapTypeFromClass(entityClass);
		return gson.fromJson(element, type);
	}

	private Gson getGson() {
		return new GsonBuilder().setDateFormat(SimpleDateFormat.DEFAULT).create();
	}

	private Type getMapTypeFromClass(Class<? extends Object> entityClass) {
		if (entityClass == Event.class) {
			return new TypeToken<Map<String, Event>>() {
			}.getType();
		} else if (entityClass == User.class) {
			return new TypeToken<Map<String, User>>() {
			}.getType();
		} else if (entityClass == Ticket.class)

		{
			return new TypeToken<Map<String, Ticket>>() {
			}.getType();
		}
		return null;
	}

	private String getPrefix(Class<? extends Object> entityClass) {
		if (entityClass == Event.class) {
			return "event:";
		} else if (entityClass == User.class) {
			return "user:";
		} else if (entityClass == Ticket.class) {
			return "ticket:";
		}
		return "";
	}

	private JsonElement createJsonElementMap(Class<? extends Object> entityClass, Map<Long, ? extends Object> map) {
		Map<String, Object> tempMap = new HashMap<>();
		Type type = getMapTypeFromClass(entityClass);
		String prefix = getPrefix(entityClass);
//		System.out.println(prefix);
		for (Entry<Long, ? extends Object> entry : map.entrySet()) {
			tempMap.put(prefix + entry.getKey(), entry.getValue());
		}
		Gson gson = getGson();
		return gson.toJsonTree(tempMap, type);
	}

	public Map<Long, Event> loadEventsWithAdapter() {
		try {
			JsonElement element = JsonParser.parseReader(readJsonObjectFromResource(savedEventsResource));
			Type type = new TypeToken<Map<Long, Event>>() {
			}.getType();
			Gson gson = new GsonBuilder().setDateFormat(DateFormat.DEFAULT)
					.registerTypeAdapter(type, new EventTypeAdapter()).create();
			return gson.fromJson(element, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean saveEventsWithAdapter(Map<Long, Event> eventsMap) {
		Type type = new TypeToken<Map<Long, Event>>() {
		}.getType();
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.DEFAULT)
				.registerTypeAdapter(type, new EventTypeAdapter()).create();
		JsonElement element = gson.toJsonTree(eventsMap, type);
		try {
			writeJsonObjectToResource(element, savedEventsResource);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean writeJsonObjectToResource(JsonElement jsonObject, Resource resource) throws Exception {
		if (!(resource.exists() && resource.isFile())) {
			System.out.println(resource.getDescription());
			throw new RuntimeException("The file to save the Entities do not exist, or is not a file");
		}
		if (!(resource instanceof WritableResource) || !((WritableResource) resource).isWritable()) {
			throw new RuntimeException("The file is not writable");
		}
		try (Writer writer = new OutputStreamWriter(
				new BufferedOutputStream(((WritableResource) resource).getOutputStream()));) {
			writer.write(jsonObject.toString().toCharArray());
			writer.flush();
		}
		return true;
	}

	private BufferedReader readJsonObjectFromResource(Resource resource) throws Exception {
		if (!(resource.exists() && resource.isFile())) {
			System.out.println(resource.getDescription());
			throw new RuntimeException("The file to save the Entities do not exist, or is not a file");
		}
		if (!resource.isReadable()) {
			throw new RuntimeException("The file is not readable");
		}
		BufferedInputStream in = new BufferedInputStream(resource.getInputStream());
		return new BufferedReader(new InputStreamReader(in));
	}

	public FileSystemResource getSavedEventsResource() {
		return savedEventsResource;
	}

	public void setSavedEventsResource(FileSystemResource savedEventsResource) {
		this.savedEventsResource = savedEventsResource;
	}

	public FileSystemResource getSavedTicketsResource() {
		return savedTicketsResource;
	}

	public void setSavedTicketsResource(FileSystemResource savedTicketsResource) {
		this.savedTicketsResource = savedTicketsResource;
	}

	public FileSystemResource getSavedUsersResource() {
		return savedUsersResource;
	}

	public void setSavedUsersResource(FileSystemResource savedUsersResource) {
		this.savedUsersResource = savedUsersResource;
	}

}
