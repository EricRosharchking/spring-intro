package com.epam.liyuan.hong.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

/**
 * Created by maksym_govorischev.
 */
public class Ticket {
	public enum Category {
		STANDARD, PREMIUM, BAR
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long eventId;
	private long userId;
	private Category category;
	private int place;

	public Ticket(long eventId, long userId, Category category, int place) {
		super();
		this.eventId = eventId;
		this.userId = userId;
		this.category = category;
		this.place = place;
	}

	/**
	 * Ticket Id. UNIQUE.
	 * 
	 * @return Ticket Id.
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

}