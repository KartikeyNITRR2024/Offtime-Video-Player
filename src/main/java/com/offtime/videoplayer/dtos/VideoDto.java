package com.offtime.videoplayer.dtos;

import com.offtime.videoplayer.entities.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VideoDto {
	private Long id;
    private String videoUrl;
    private Long lastStopTime;
    private User user;
}
