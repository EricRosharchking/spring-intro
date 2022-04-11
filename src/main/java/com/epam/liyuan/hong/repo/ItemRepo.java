package com.epam.liyuan.hong.repo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.epam.liyuan.hong.model.Event;
import com.epam.liyuan.hong.model.Ticket;
import com.epam.liyuan.hong.model.User;
import com.epam.liyuan.hong.util.EventTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
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

	public Map<Long, Event> loadEvents() {
		Map<String, Object> tempMap = new HashMap<>();
		try {
			tempMap = readTempMapFromResource(Event.class, savedEventsResource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int prefixLength = getPrefixLength(Event.class);
		Map<Long, Event> resMap = new HashMap<>();
		tempMap.entrySet().stream().forEach(
				entry -> resMap.put(Long.valueOf(entry.getKey().substring(prefixLength)), (Event) entry.getValue()));
		return resMap;
	}

	public boolean saveEvents(Map<Long, Event> eventsMap) {
		JsonElement element = createJsonElementMap(Event.class, eventsMap);
		try {
			writeJsonObjectToResource(element, savedEventsResource);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Map<Long, User> loadUsers() {
		Map<String, Object> tempMap = new HashMap<>();
		try {
			tempMap = readTempMapFromResource(User.class, savedUsersResource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int prefixLength = getPrefixLength(User.class);
		Map<Long, User> resMap = new HashMap<>();
		tempMap.entrySet().stream().forEach(
				entry -> resMap.put(Long.valueOf(entry.getKey().substring(prefixLength)), (User) entry.getValue()));
		return resMap;
	}

	public boolean saveUsers(Map<Long, User> usersMap) {
		JsonElement element = createJsonElementMap(User.class, usersMap);
		try {
			writeJsonObjectToResource(element, savedEventsResource);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Map<Long, Ticket> loadTickets() {
		Map<String, Object> tempMap = new HashMap<>();
		try {
			tempMap = readTempMapFromResource(Ticket.class, savedTicketsResource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int prefixLength = getPrefixLength(Ticket.class);
		Map<Long, Ticket> resMap = new HashMap<>();
		tempMap.entrySet().stream().forEach(
				entry -> resMap.put(Long.valueOf(entry.getKey().substring(prefixLength)), (Ticket) entry.getValue()));
		return resMap;
	}

	public boolean saveTickets(Map<Long, Ticket> ticketsMap) {
		JsonElement element = createJsonElementMap(Ticket.class, ticketsMap);
		try {
			writeJsonObjectToResource(element, savedEventsResource);
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
		Type type = getTypeFromClass(entityClass);
		return gson.fromJson(element, type);
	}

	private Gson getGson() {
		return new GsonBuilder().setDateFormat(SimpleDateFormat.DEFAULT).create();
	}

	private Type getTypeFromClass(Class<? extends Object> entityClass) {
		if (entityClass == Event.class) {
			return new TypeToken<Map<String, Event>>() {
			}.getType();
		} else if (entityClass == User.class) {
			return new TypeToken<Map<String, User>>() {
			}.getType();
		} else if (entityClass == Ticket.class) {
			return new TypeToken<Map<String, Ticket>>() {
			}.getType();
		}
		return null;
	}

	private int getPrefixLength(Class<? extends Object> entityClass) {
		if (entityClass == Event.class) {
			return "event:".length();
		} else if (entityClass == User.class) {
			return "user:".length();
		} else if (entityClass == Ticket.class) {
			return "ticket:".length();
		}
		return 0;
	}

	private JsonElement createJsonElementMap(Class<? extends Object> entityClass, Map<Long, ? extends Object> map) {
		Map<String, Object> tempMap = new HashMap<>();
		Type type = getTypeFromClass(entityClass);
		for (Entry<Long, ? extends Object> entry : map.entrySet()) {
			tempMap.put("event:" + entry.getKey(), entry.getValue());
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
//		WritableResource writableResource = (WritableResource) resource;
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
