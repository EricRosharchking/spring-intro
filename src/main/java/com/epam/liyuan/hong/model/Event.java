package com.epam.liyuan.hong.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.json.JSONPropertyName;
import org.springframework.data.annotation.Id;

/**
 * Created by maksym_govorischev.
 */
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private Date date;

	public Event(long id, String title, Date date) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
	}

	/**
	 * Event id. UNIQUE.
	 * 
	 * @return Event Id
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

}
