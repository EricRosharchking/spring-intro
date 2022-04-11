package com.epam.liyuan.hong.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.epam.liyuan.hong.model.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class EventTypeAdapter extends TypeAdapter<Map<Long, Event>> {

	public void write(JsonWriter out, Map<Long, Event> map) throws IOException {
		if (map == null || map.isEmpty()) {
			out.nullValue();
			return;
		}
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.DEFAULT).create();
		Type type = new TypeToken<Event>() {
		}.getType();
		out.beginObject();
		for (Entry<Long, Event> entry : map.entrySet()) {
			out.name("event:" + entry.getValue().getId());
			gson.toJson(entry.getValue(), type, out);
			out.flush();
		}
		out.endObject().flush();
	}

	public Map<Long, Event> read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		Map<Long, Event> result = new HashMap<>();
		in.beginObject();
		while (in.hasNext()) {
//			long id = Long.valueOf(in.nextName().substring("event:".length()));
			String name = in.nextName();
			System.out.println(name);
			long id = Long.valueOf(name.substring("event:".length()));
			try {
				Event event = readEvent(in);
				result.put(id, event);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IOException();
			}
		}
		in.endObject();
		return result;
	}

	private Event readEvent(JsonReader in) throws IOException, ParseException {
		long id = -1;
		String title = null;
		Date date = null;

		in.beginObject();
		while (in.hasNext()) {
			String name = in.nextName();
			switch (name) {
			case "id":
				id = in.nextLong();
				break;
			case "title":
				title = in.nextString();
				break;
			case "date":
				date = DateFormat.getDateInstance(DateFormat.DEFAULT).parse(in.nextString());
				break;
			default:
				in.skipValue();
			}
		}
		in.endObject();
		return new Event(id, title, date);
	}

}
