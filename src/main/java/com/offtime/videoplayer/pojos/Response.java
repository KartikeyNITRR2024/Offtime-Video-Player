package com.offtime.videoplayer.pojos;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.offtime.videoplayer.utils.PropertyLoader;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response<T> {
	private Boolean success;
    private T data;
    private String message;
    private int status;
    private LocalDateTime timestamp;
    
    public Response() {
        this.timestamp = LocalDateTime.now(ZoneId.of(PropertyLoader.getConfigTable().getTimeZone()));
    }
    
    public Response(Boolean success, T data, int status) {
        this.success = success;
        this.data = data;
        this.status = status;
        this.timestamp = LocalDateTime.now(ZoneId.of(PropertyLoader.getConfigTable().getTimeZone()));
    }
    
    public Response(Boolean success, String message, int status) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now(ZoneId.of(PropertyLoader.getConfigTable().getTimeZone()));
    }
     
}