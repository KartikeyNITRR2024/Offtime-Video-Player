package com.offtime.videoplayer.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offtime.videoplayer.enums.WorkId;
import com.offtime.videoplayer.enums.WorkType;
import com.offtime.videoplayer.exceptions.InvalidWorkException;
import com.offtime.videoplayer.pojos.WebsocketResponse;
import com.offtime.videoplayer.services.UserService;
import com.offtime.videoplayer.services.VideoService;
import com.offtime.videoplayer.services.WebsocketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.offtime.videoplayer.dtos.UserDto;
import com.offtime.videoplayer.dtos.VideoDto;

@Service
public class WebsocketServiceImpl implements WebsocketService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
    private ObjectMapper objectMapper;

	@Override
	public WebsocketResponse<Object> doWork(WebsocketResponse<Object> request) throws InvalidWorkException
	{
		WebsocketResponse<Object> response = null;
		Object res = null;
//		if(request.getWorkType() == WorkType.USER)
//		{
//			UserDto userDto = null;
//			if(request.getWorkId() == WorkId.VALIDATECODE)
//			{
//				res = userService.isUniqueCodePresent(request.getUniqueCode());
//			}
//			else if(request.getWorkId() == WorkId.CREATEORUPDATECODE)
//			{
//				userDto = objectMapper.convertValue(request.getPayload(), UserDto.class);
//				if(userDto == null)
//		    	{
//		    		throw new InvalidWorkException(
//		                    String.format("Payload is empty."));
//		    	}
//				res = userService.createOrUpdateUser(userDto);
//			}
//			response = new WebsocketResponse<Object>(request, true, res);
//		}
//		else if(request.getWorkType() == WorkType.VIDEO)
//		{
//			VideoDto videoDto = null;
//			if(request.getWorkId() == WorkId.SAVEVIDEO)
//			{
//				videoDto = objectMapper.convertValue(request.getPayload(), VideoDto.class);
//				if(videoDto == null)
//		    	{
//		    		throw new InvalidWorkException(
//		                    String.format("Payload is empty."));
//		    	}
//				res = videoService.addVideo(videoDto, request.getUniqueCode());
//			}
//			else if(request.getWorkId() == WorkId.SETCURRENTVIDEO)
//			{
//				videoDto = objectMapper.convertValue(request.getPayload(), VideoDto.class);
//				if(videoDto == null)
//		    	{
//		    		throw new InvalidWorkException(
//		                    String.format("Payload is empty."));
//		    	}
//				res = videoService.updateCurrentVideo(videoDto, request.getUniqueCode());
//			}
//			else if(request.getWorkId() == WorkId.UPDATEVIDEO)
//			{
//				videoDto = objectMapper.convertValue(request.getPayload(), VideoDto.class);
//				if(videoDto == null)
//		    	{
//		    		throw new InvalidWorkException(
//		                    String.format("Payload is empty."));
//		    	}
//				res = videoService.updateCurrentVideo(videoDto, request.getUniqueCode());
//			}
//			else if(request.getWorkId() == WorkId.DELETEVIDEO)
//			{
//				videoDto = objectMapper.convertValue(request.getPayload(), VideoDto.class);
//				if(videoDto == null)
//		    	{
//		    		throw new InvalidWorkException(
//		                    String.format("Payload is empty."));
//		    	}
//				videoService.deleteVideoByUser(videoDto, request.getUniqueCode());
//			}
//			else if(request.getWorkId() == WorkId.GETALLVIDEOS)
//			{
//				res = videoService.getAllVideoByUser(request.getUniqueCode());
//			}
//			else if(request.getWorkId() == WorkId.GETCURRENTVIDEO)
//			{
//                res = videoService.getCurrentVideoByUser(request.getUniqueCode());
//			}
//			response = new WebsocketResponse<Object>(request, true, res);
//		}
		if(request.getWorkType() == WorkType.VIDEO)
		{
			VideoDto videoDto = null;
			if(request.getWorkId() == WorkId.SETCURRENTVIDEO)
			{
				videoDto = objectMapper.convertValue(request.getPayload(), VideoDto.class);
				if(videoDto == null)
			    {
			    	throw new InvalidWorkException(
			                   String.format("Payload is empty."));
			    }
				res = videoService.updateCurrentVideo(videoDto, request.getUniqueCode());
			}
			else if(request.getWorkId() == WorkId.PLAY_PAUSE)
			{
				if(request.getPayload() == Boolean.TRUE)
				{
					res = Boolean.TRUE;
				}
				else
				{
					res = Boolean.FALSE;
				}
			}
			else if(request.getWorkId() == WorkId.PLAYINLOOP)
			{
				if(request.getPayload() == Boolean.TRUE)
				{
					res = Boolean.TRUE;
				}
				else
				{
					res = Boolean.FALSE;
				}
			}
			else if(request.getWorkId() == WorkId.MUTEAUDIO)
			{
				if(request.getPayload() == Boolean.TRUE)
				{
					res = Boolean.TRUE;
				}
				else
				{
					res = Boolean.FALSE;
				}
			}
			response = new WebsocketResponse<Object>(request, true, res);
		}
		if(response == null)
    	{
    		throw new InvalidWorkException(
                    String.format("Invalid worktype or id."));
    	}
		return response;
	}
}
