package com.offtime.videoplayer.services;

import com.offtime.videoplayer.dtos.UserDto;
import com.offtime.videoplayer.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserByUniqueCode(String uniqueCode);
    List<User> getAllUsers();
    User updateUser(String uniqueCode, User updatedUser);
    void deleteUser(String uniqueCode);
	boolean isUniqueCodePresent(String uniqueCode);
	UserDto createOrUpdateUser(UserDto userRequest);
	long getTotalUsers();
	void updateUserLastUsedDateTime(String uniqueCode);
}
