package com.offtime.videoplayer.pojos;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.offtime.videoplayer.enums.WorkId;
import com.offtime.videoplayer.enums.WorkType;
import com.offtime.videoplayer.utils.PropertyLoader;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WebsocketResponse<T> {
    private Integer pathUniqueId;
    private WorkType workType;
    private String uniqueCode;
    private WorkId workId;
    private T data;
    private Boolean success;
    private String message;
    private LocalDateTime timestamp;
    private Object payload;


    public WebsocketResponse() {
        this.timestamp = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }

    public WebsocketResponse(WebsocketResponse<T> websocketResponse) {
        this.workType = websocketResponse.getWorkType();
        this.workId = websocketResponse.getWorkId();
        this.uniqueCode = websocketResponse.getUniqueCode();
        this.timestamp = LocalDateTime.now(ZoneId.of(PropertyLoader.getConfigTable().getTimeZone()));
    }

    public WebsocketResponse(WebsocketResponse<T> websocketResponse, Boolean success, T data) {
        this.workType = websocketResponse.getWorkType();
        this.workId = websocketResponse.getWorkId();
        this.uniqueCode = websocketResponse.getUniqueCode();
        this.success = success;
        this.data = data;
        this.timestamp = LocalDateTime.now(ZoneId.of(PropertyLoader.getConfigTable().getTimeZone()));
    }

    public WebsocketResponse(WebsocketResponse<T> websocketResponse, Boolean success, String message) {
        this.workType = websocketResponse.getWorkType();
        this.workId = websocketResponse.getWorkId();
        this.uniqueCode = websocketResponse.getUniqueCode();
        this.success = success;
        this.message = message;
        this.timestamp = LocalDateTime.now(ZoneId.of(PropertyLoader.getConfigTable().getTimeZone()));
    }
}
