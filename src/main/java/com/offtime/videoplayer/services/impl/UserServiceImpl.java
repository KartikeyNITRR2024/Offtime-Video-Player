package com.offtime.videoplayer.services.impl;

import com.offtime.videoplayer.controllers.UserController;
import com.offtime.videoplayer.dtos.UserDto;
import com.offtime.videoplayer.entities.User;
import com.offtime.videoplayer.entities.Video;
import com.offtime.videoplayer.exceptions.InvalidUserException;
import com.offtime.videoplayer.repos.UserRepository;
import com.offtime.videoplayer.services.UserService;
import com.offtime.videoplayer.utils.DtoEntityConverter;
import com.offtime.videoplayer.utils.PropertyLoader;
import com.offtime.videoplayer.validations.UserValidations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserValidations userValidations;
    
    @Autowired
    private PropertyLoader propertyLoader;
    
    @Autowired
    private DtoEntityConverter dtoEntityConverter;
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUniqueCode(String uniqueCode) {
    	updateUserLastUsedDateTime(uniqueCode);
        return userRepository.findByUniqueCode(uniqueCode);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(String uniqueCode, User updatedUser) {
        return userRepository.findByUniqueCode(uniqueCode).map(existingUser -> {
            existingUser.setLastUsedDateTime(LocalDateTime.now());
            if(updatedUser.getCurrentVideo() != null)
            {
               existingUser.setCurrentVideo(updatedUser.getCurrentVideo());
            }
            if(updatedUser.getVideos() != null)
            {
              existingUser.setVideos(updatedUser.getVideos());	
            }
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new InvalidUserException(
                String.format("Invalid user: %s",uniqueCode)));
    }
    
    @Override
    public void updateUserLastUsedDateTime(String uniqueCode)
    {
    	userRepository.findByUniqueCode(uniqueCode).map(existingUser -> {
            existingUser.setLastUsedDateTime(LocalDateTime.now());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new InvalidUserException(
                String.format("Invalid user: %s",uniqueCode)));
    }

    @Override
    public void deleteUser(String uniqueCode) {
        userRepository.findByUniqueCode(uniqueCode).ifPresent(userRepository::delete);
    }
    
    @Override
    public boolean isUniqueCodePresent(String uniqueCode) {
        return userRepository.findByUniqueCode(uniqueCode).isPresent();
    }
    
    @Override
    public UserDto createOrUpdateUser(UserDto userRequest) {
    	userValidations.validateUser(userRequest.getUniqueCode());
        Optional<User> optionalUser = userRepository.findByUniqueCode(userRequest.getUniqueCode());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setLastUsedDateTime(LocalDateTime.now());
            User updatedUser = userRepository.save(existingUser);
        	return dtoEntityConverter.userToDto(updatedUser);
        } else {
        	if(getTotalUsers() >= propertyLoader.configTable.getMaxUsers())
        	{
        		throw new InvalidUserException(
                        String.format("Maximum users reached. Please contact Admin"));
        	}
            userRequest.setLastUsedDateTime(LocalDateTime.now());
            return dtoEntityConverter.userToDto(userRepository.save(dtoEntityConverter.dtoToUser(userRequest)));
        }
    }
    
    
    @Override
    public long getTotalUsers() {
        return userRepository.count();
    }
}
