package com.pentalog.dto;

/**
 * Data transfer object class for the model User
 * 
 * @author Vacariuc Bogdan
 *
 */

public class UserDTO {

	private String username;

	private String password;

	public UserDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
