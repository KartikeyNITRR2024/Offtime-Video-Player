package com.offtime.videoplayer.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
	private Long id;
    private String uniqueCode;
    private LocalDateTime lastUsedDateTime;
    private List<VideoDto> videos;
    private VideoDto currentVideo;
}
