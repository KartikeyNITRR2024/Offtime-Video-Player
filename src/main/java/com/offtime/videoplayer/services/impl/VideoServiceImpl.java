package com.offtime.videoplayer.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offtime.videoplayer.controllers.VideoController;
import com.offtime.videoplayer.dtos.VideoDto;
import com.offtime.videoplayer.entities.User;
import com.offtime.videoplayer.entities.Video;
import com.offtime.videoplayer.exceptions.InvalidUserException;
import com.offtime.videoplayer.exceptions.InvalidVideoException;
import com.offtime.videoplayer.repos.VideoRepository;
import com.offtime.videoplayer.services.UserService;
import com.offtime.videoplayer.services.VideoService;
import com.offtime.videoplayer.utils.DtoEntityConverter;
import com.offtime.videoplayer.utils.PropertyLoader;
import com.offtime.videoplayer.validations.VideoValidations;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	UserService userService;
	
	@Autowired
	private VideoRepository videoRepository;
	
	@Autowired
	private VideoValidations videoValidations;
	
    @Autowired
    private PropertyLoader propertyLoader;
    
    @Autowired
    private DtoEntityConverter dtoEntityConverter;
    
    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);
	
	@Override
	public VideoDto addVideo(VideoDto videoDto, String uniqueCode)
	{
		videoValidations.validateVideoUrl(videoDto.getVideoUrl());
		videoDto.setLastStopTime(0L);
		Optional<User> opt = userService.getUserByUniqueCode(uniqueCode);
		if(opt.isEmpty())
		{
			throw new InvalidUserException(
	                String.format("Invalid user: %s",uniqueCode));
		}
		User user = opt.get();
		if(countVideosByUserId(user.getId()) >= propertyLoader.configTable.getMaxVideosPerUser())
		{
			throw new InvalidVideoException(
	                String.format("Any user can add only %d videos.", propertyLoader.configTable.getMaxVideosPerUser()));
		}
		if(findByVideoUrlAndUserId(videoDto.getVideoUrl(), user.getId()).isPresent())
		{
			throw new InvalidVideoException(
	                String.format("Video is allready present."));
		}
		Video video = dtoEntityConverter.dtoToVideo(videoDto, user);
		return dtoEntityConverter.videoToDto(videoRepository.save(video));
	}
	
	@Override
	@Transactional
	public void deleteVideoByUser(VideoDto videoDto, String uniqueCode)
	{
		Optional<Video> vidOpt = videoRepository.findById(videoDto.getId());
		if(vidOpt.isEmpty())
		{
			throw new InvalidVideoException(
	                String.format("Invalid video"));
		}
		Video video = vidOpt.get();
		User user = video.getUser();
		if(!user.getUniqueCode().equals(uniqueCode))
		{
			throw new InvalidUserException(
	                String.format("Invalid user: %s",uniqueCode));
		}
		if(user.getCurrentVideo().getId() == videoDto.getId())
		{
			user.setCurrentVideo(null);
			User updatedUser = userService.updateUser(user.getUniqueCode(), user);
		}
		videoRepository.delete(video);
	}
	
	@Override
	@Transactional
	public void deleteAllVideoByUser(String uniqueCode)
	{
		Optional<User> opt = userService.getUserByUniqueCode(uniqueCode);
		if(opt.isEmpty())
		{
			throw new InvalidUserException(
	                String.format("Invalid user: %s",uniqueCode));
		}
		User user = opt.get();
		user.setCurrentVideo(null);
		User updatedUser = userService.updateUser(user.getUniqueCode(), user);
		videoRepository.deleteByUserId(user.getId());
	}
	
	@Override
	@Transactional
	public VideoDto updateVideo(VideoDto videoDto, String uniqueCode)
	{
		Optional<Video> vidOpt = videoRepository.findById(videoDto.getId());
		if(vidOpt.isEmpty())
		{
			throw new InvalidVideoException(
	                String.format("Invalid video"));
		}
		Video video = vidOpt.get();
		User user = video.getUser();
		if(!user.getUniqueCode().equals(uniqueCode))
		{
			throw new InvalidUserException(
	                String.format("Invalid user: %s",uniqueCode));
		}
		userService.updateUserLastUsedDateTime(uniqueCode);
		if(videoDto.getVideoUrl() != null)
		{
			if(findByVideoUrlAndUserId(videoDto.getVideoUrl(), user.getId()).isPresent())
			{
				throw new InvalidVideoException(
		                String.format("Video is allready present."));
			}
		   videoValidations.validateVideoUrl(videoDto.getVideoUrl());
		   video.setVideoUrl(videoDto.getVideoUrl());
		}
		if(videoDto.getLastStopTime() != null)
		{
		   video.setLastStopTime(videoDto.getLastStopTime());
		}
		if(videoDto.getVideoName() != null)
		{
			video.setVideoName(videoDto.getVideoName());
		}
		return dtoEntityConverter.videoToDto(videoRepository.save(video));
	}
	
	@Override
	public VideoDto updateCurrentVideo(VideoDto videoDto, String uniqueCode)
	{
		Optional<Video> vidOpt = videoRepository.findById(videoDto.getId());
		if(vidOpt.isEmpty())
		{
			throw new InvalidVideoException(
	                String.format("Video not found"));
		}
		Video video = vidOpt.get();
		User user = video.getUser();
		if(!user.getUniqueCode().equals(uniqueCode))
		{
			throw new InvalidUserException(
	                String.format("Invalid user: %s",uniqueCode));
		}
		user.setCurrentVideo(video);
		User updatedUser = userService.updateUser(user.getUniqueCode(), user);
		video.setUser(updatedUser);
		return dtoEntityConverter.videoToDto(videoRepository.save(video));
	}
	
	@Override
	public long countVideosByUserId(Long userId) {
	    return videoRepository.countByUserId(userId);
	}
	
	@Override
	public long countVideosByUser(String uniqueCode) {
		Optional<User> opt = userService.getUserByUniqueCode(uniqueCode);
		if(opt.isEmpty())
		{
			throw new InvalidUserException(
	                String.format("Invalid user: %s",uniqueCode));
		}
		User user = opt.get();
	    return videoRepository.countByUserId(user.getId());
	}
	
	@Override
	public Optional<Video> findByVideoUrlAndUserId(String videoUrl, Long userId) {
	    return videoRepository.findByVideoUrlAndUserId(videoUrl, userId);
	}
	
	
}
