package com.epam.liyuan.hong.repo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

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
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Component
public class ItemRepo {

	@Autowired
	@Qualifier(value = "eventFileResource")
	private FileSystemResource savedEventsResource;
	@Autowired
	@Qualifier(value = "ticketFileResource")
	private FileSystemResource savedTicketsResource;
	@Autowired
	@Qualifier(value = "userFileResource")
	private FileSystemResource savedUsersResource;

	public Map<Long, Event> loadEvents() {

		StringBuffer buffer = new StringBuffer();
		try {
			JsonElement element = JsonParser.parseReader(readJsonObjectFromResource(savedEventsResource));
			return (Map<Long, Event>) element;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean saveEvents(Map<Long, Event> eventsMap) {

		JSONObject jsonObject = new JSONObject(eventsMap);
//		savedEventsResource.getOutputStream();
		try {
			writeJsonObjectToResource(jsonObject, savedEventsResource);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean writeJsonObjectToResource(JSONObject jsonObject, WritableResource resource) throws Exception {
		if (!(resource.exists() && resource.isFile())) {
			System.out.println(resource.getDescription());
			throw new RuntimeException("The file to save the Entities do not exist, or is not a file");
		}
		if (!resource.isWritable()) {
			throw new RuntimeException("The file is not writable");
		}
		try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(resource.getOutputStream()));) {
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