package com.epam.liyuan.hong.model;

import java.util.Objects;

/**
 * Created by maksym_govorischev.
 */
//@Entity
public class Ticket implements Cloneable {

	public enum Category {
		STANDARD, PREMIUM, BAR
	}

	private static long idSequence = 0;
	private long id;
	private long eventId;
	private long userId;
	private Category category;
	private int place;

	public Ticket(long eventId, long userId, Category category, int place) {
		super();
		this.id = generateId();
		this.eventId = eventId;
		this.userId = userId;
		this.category = category;
		this.place = place;
	}

	private static long generateId() {
		return idSequence++;
	}

	public static void setSequence(long sequence) {
		idSequence = ++sequence;
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

	@Override
	public int hashCode() {
		return Objects.hash(category, eventId, id, place, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return category == other.category && eventId == other.eventId && id == other.id && place == other.place
				&& userId == other.userId;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", eventId=" + eventId + ", userId=" + userId + ", category=" + category
				+ ", place=" + place + "]";
	}

	@Override
	public Ticket clone() {
		return new Ticket(this.id, this.eventId, this.userId, this.category, this.place);
	}

	private Ticket(long id, long eventId, long userId, Category category, int place) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.userId = userId;
		this.category = category;
		this.place = place;
	}

}
