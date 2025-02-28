package com.pentalog.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Model class for User
 * 
 * @author Vacariuc Bogdan
 *
 */

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<Account> accounts = new ArrayList<>();

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "created_time")
	private LocalDateTime createdTime;

	@Column(name = "updated_time")
	private LocalDateTime updatedTime;

	/**
	 * Constructor without parameters. Only sets createdTime and updatedTime.
	 */
	public User() {
		setTime();
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	private void setTime() {
		createdTime = LocalDateTime.now();
		updatedTime = LocalDateTime.now();
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		updatedTime = LocalDateTime.now();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		updatedTime = LocalDateTime.now();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		updatedTime = LocalDateTime.now();
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
		updatedTime = LocalDateTime.now();
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
		updatedTime = LocalDateTime.now();
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
		updatedTime = LocalDateTime.now();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
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
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
