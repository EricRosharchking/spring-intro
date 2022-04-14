package com.epam.liyuan.hong.model;

import java.util.Date;
import java.util.Objects;

/**
 * Created by maksym_govorischev.
 */
public class Event implements Cloneable{

	private static long idSequence = 0;

	private long id;
	private String title;
	private Date date;

	public Event(String title, Date date) {
		super();
		id = generateId();
		this.title = title;
		this.date = date;
	}

	private static synchronized long generateId() {
		return idSequence++;
	}
	
	public static void setSequence(long sequence) {
		idSequence = ++sequence;
	}

	/**
	 * Event id. UNIQUE.
	 * 
	 * @return Event Id
	 */
	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", date=" + date + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(date, other.date) && id == other.id && Objects.equals(title, other.title);
	}

	@Override
	public Event clone() {
		return new Event(this.id, this.title, this.date);
	}
	
	private Event(long id, String title, Date date) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
	}

}
