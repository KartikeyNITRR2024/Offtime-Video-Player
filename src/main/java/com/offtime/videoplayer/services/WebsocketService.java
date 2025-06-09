package com.offtime.videoplayer.services;

import com.offtime.videoplayer.exceptions.InvalidWorkException;
import com.offtime.videoplayer.pojos.WebsocketResponse;

public interface WebsocketService {

	WebsocketResponse<Object> doWork(WebsocketResponse<Object> request) throws InvalidWorkException;

}
