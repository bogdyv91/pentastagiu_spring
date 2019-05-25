package com.pentalog.converter;

import org.springframework.stereotype.Component;

import com.pentalog.dto.UserDTO;
import com.pentalog.model.User;


/**
 * Contains method to convert from User to its data transfer object User and vice versa
 * @author Vacariuc Bogdan
 *
 */
@Component
public class UserConverter {

	/**
	 * Converts from UserDTO to User
	 * @param userDto the dto object that has to be converted
	 * @return the object converted from the his data transfer object
	 */
	
	public User convertFromUserDTO(UserDTO userDto) {
		return new User(userDto.getUsername(), userDto.getPassword());
	}

	
	/**
	 * Converts from User
	 * @param user the object that has to be converted
	 * @return the dto object of the given parameter
	 */
	public UserDTO convertToUserDTO(User user) {
		return new UserDTO(user.getUsername(), user.getPassword());
	}

}
