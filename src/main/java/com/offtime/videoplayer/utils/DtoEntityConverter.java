package com.offtime.videoplayer.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.offtime.videoplayer.dtos.UserDto;
import com.offtime.videoplayer.dtos.VideoDto;
import com.offtime.videoplayer.entities.User;
import com.offtime.videoplayer.entities.Video;

@Component
public class DtoEntityConverter {
	public VideoDto videoToDto(Video video)
	{
		if(video == null)
		{
			return null;
		}
		VideoDto videoDto = new VideoDto();
		videoDto.setId(video.getId());
		videoDto.setVideoName(video.getVideoName());
		videoDto.setVideoUrl(video.getVideoUrl());
		videoDto.setLastStopTime(video.getLastStopTime());
		return videoDto;
	}
	
	public Video dtoToVideo(VideoDto videoDto, User user)
	{
		if(videoDto == null)
		{
			return null;
		}
		Video video = new Video();
		video.setId(videoDto.getId());
		video.setVideoName(videoDto.getVideoName());
		video.setVideoUrl(videoDto.getVideoUrl());
		video.setLastStopTime(videoDto.getLastStopTime());
		video.setUser(user);
		return video;
	}
	
	public User dtoToUser(UserDto userDto)
    {
		if(userDto == null)
		{
			return null;
		}
    	User user = new User();
    	user.setId(userDto.getId());
    	user.setLastUsedDateTime(userDto.getLastUsedDateTime());
    	user.setUniqueCode(userDto.getUniqueCode());
    	return user;
    }
    
    public UserDto userToDto(User user)
    {
    	if(user == null)
		{
			return null;
		}
    	UserDto userDto = new UserDto();
    	userDto.setLastUsedDateTime(user.getLastUsedDateTime());
    	userDto.setUniqueCode(user.getUniqueCode());
    	if(user.getCurrentVideo() != null)
    	{
    		userDto.setCurrentVideo(videoToDto(user.getCurrentVideo()));
    	}
		List<VideoDto> videoDtos = new ArrayList<>();
    	if(user.getVideos() != null && user.getVideos().size() > 0)
    	{
    		for(Video video : user.getVideos())
    		{
    			videoDtos.add(videoToDto(video));
    		}
    	}
		userDto.setVideos(videoDtos);
    	return userDto;
    }
}
