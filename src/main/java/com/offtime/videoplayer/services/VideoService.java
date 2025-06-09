package com.offtime.videoplayer.services;

import java.util.List;
import java.util.Optional;

import com.offtime.videoplayer.dtos.VideoDto;
import com.offtime.videoplayer.entities.User;
import com.offtime.videoplayer.entities.Video;

public interface VideoService {

	VideoDto updateVideo(VideoDto videoDto, String uniqueCode);

	VideoDto updateCurrentVideo(VideoDto videoDto, String uniqueCode);

	VideoDto addVideo(VideoDto videoDto, String uniqueCode);

	void deleteAllVideoByUser(String uniqueCode);

	void deleteVideoByUser(VideoDto videoDto, String uniqueCode);

	Optional<Video> findByVideoUrlAndUserId(String videoUrl, Long userId);

	long countVideosByUser(String uniqueCode);

	long countVideosByUserId(Long userId);

	List<VideoDto> getAllVideoByUser(String uniqueCode);

	VideoDto getCurrentVideoByUser(String uniqueCode);

}
