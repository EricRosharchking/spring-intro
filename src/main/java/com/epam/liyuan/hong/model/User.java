package com.epam.liyuan.hong.model;

import java.util.Objects;

/**
 * Created by maksym_govorischev on 14/03/14.
 */
public class User implements Cloneable {

	private static long idSequence = 0;

	private long id;
	private String name;
	private String email;

	public User(String name, String email) {
		super();
		id = generateId();
		this.name = name;
		this.email = email.toLowerCase();
	}

	private static synchronized long generateId() {
		return idSequence++;
	}

	public static void setSequence(long sequence) {
		idSequence = ++sequence;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * User email. UNIQUE.
	 * 
	 * @return User email.
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && id == other.id && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + "]";
	}

	@Override
	public User clone() {
		return new User(this.id, this.name, this.email);
	}

	private User(long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

}
