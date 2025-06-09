package com.offtime.videoplayer.controllers.websocketcontrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.offtime.videoplayer.exceptions.InvalidWorkException;
import com.offtime.videoplayer.pojos.WebsocketResponse;
import com.offtime.videoplayer.services.WebsocketService;
import com.offtime.videoplayer.validations.UniversalValidations;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class WebsocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private UniversalValidations universalValidations;
    
    @Autowired
    private WebsocketService websocketService;
    
    private static final Logger logger = LoggerFactory.getLogger(WebsocketController.class);
    
    
    @MessageMapping("/chat")
    public void handleMessage(@Payload WebsocketResponse<Object> request) {
    	universalValidations.validateUniqueId(request.getPathUniqueId());
        logger.info("Request data is : {}", request);
        WebsocketResponse<Object> response = null;
        try {
            response = websocketService.doWork(request);
        } 
        catch (InvalidWorkException ex)
        {
        	response = new WebsocketResponse<Object>(request, false, ex.getMessage());
        }
        String uniqueCode = request.getUniqueCode(); 
        logger.info("uniqueCode {}", uniqueCode);
        messagingTemplate.convertAndSend("/queue/" + uniqueCode, response);
    }
}

