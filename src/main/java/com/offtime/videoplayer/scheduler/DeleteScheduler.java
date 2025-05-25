package com.offtime.videoplayer.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.offtime.videoplayer.entities.User;
import com.offtime.videoplayer.services.UserService;
import com.offtime.videoplayer.services.VideoService;
import com.offtime.videoplayer.utils.PropertyLoader;

@Component
public class DeleteScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(DeleteScheduler.class);
	
	@Autowired
	private PropertyLoader propertyLoader;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VideoService videoService;
	
	@Scheduled(cron = "0 0 0 * * ?")
    public void deleteUser() {
		logger.info("Going to delete inactive users");
		
		List<User> userList = userService.getAllUsers();
		int count = 0;
		
		LocalDateTime now = LocalDateTime.now();
		long maxInactivitySeconds = propertyLoader.configTable.getMaxTimeOfInactivityInSeconds();
		
		for(User user : userList)
		{
			LocalDateTime lastUsed = user.getLastUsedDateTime();
			if(lastUsed != null) {
				long secondsInactive = Duration.between(lastUsed, now).getSeconds();
				if(secondsInactive > maxInactivitySeconds)
				{
					videoService.deleteAllVideoByUser(user.getUniqueCode());
					userService.deleteUser(user.getUniqueCode());
					count++;
				}
			}
		}
		
		logger.info("Deleted {} inactive users", count);
    }
}
