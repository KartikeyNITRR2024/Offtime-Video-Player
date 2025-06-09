package com.offtime.videoplayer.controllers.apicontrollers;

import com.offtime.videoplayer.dtos.VideoDto;
import com.offtime.videoplayer.pojos.Response;
import com.offtime.videoplayer.services.VideoService;
import com.offtime.videoplayer.validations.UniversalValidations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;

    @Autowired
    private UniversalValidations universalValidations;

    @PostMapping("/{uniquePathId}/{uniqueCode}/add")
    public ResponseEntity<Response<VideoDto>> addVideo(@PathVariable Integer uniquePathId, @PathVariable String uniqueCode, @RequestBody VideoDto videoDto) {
        universalValidations.validateUniqueId(uniquePathId);
        logger.info("Adding video for user: {}", uniqueCode);
        VideoDto savedVideo = videoService.addVideo(videoDto, uniqueCode);
        return ResponseEntity.ok(new Response<>(true, savedVideo, 201));
    }

    @DeleteMapping("/{uniquePathId}/{uniqueCode}/delete")
    public ResponseEntity<Response<String>> deleteVideo(@PathVariable Integer uniquePathId, @PathVariable String uniqueCode, @RequestBody VideoDto videoDto) {
        universalValidations.validateUniqueId(uniquePathId);
        logger.info("Deleting video with ID {} for user: {}", videoDto.getId(), uniqueCode);
        videoService.deleteVideoByUser(videoDto, uniqueCode);
        return ResponseEntity.ok(new Response<>(true, "Video deleted successfully.", 200));
    }

    @DeleteMapping("/{uniquePathId}/{uniqueCode}/deleteAll")
    public ResponseEntity<Response<String>> deleteAllVideos(@PathVariable Integer uniquePathId, @PathVariable String uniqueCode) {
        universalValidations.validateUniqueId(uniquePathId);
        logger.info("Deleting all videos for user: {}", uniqueCode);
        videoService.deleteAllVideoByUser(uniqueCode);
        return ResponseEntity.ok(new Response<>(true, "All videos deleted successfully.", 200));
    }

    @PutMapping("/{uniquePathId}/{uniqueCode}/update")
    public ResponseEntity<Response<VideoDto>> updateVideo(@PathVariable Integer uniquePathId, @PathVariable String uniqueCode, @RequestBody VideoDto videoDto) {
        universalValidations.validateUniqueId(uniquePathId);
        logger.info("Updating video ID {} for user: {}", videoDto.getId(), uniqueCode);
        VideoDto updatedVideo = videoService.updateVideo(videoDto, uniqueCode);
        return ResponseEntity.ok(new Response<>(true, updatedVideo, 200));
    }

    @PutMapping("/{uniquePathId}/{uniqueCode}/setCurrent")
    public ResponseEntity<Response<VideoDto>> updateCurrentVideo(@PathVariable Integer uniquePathId, @PathVariable String uniqueCode, @RequestBody VideoDto videoDto) {
        universalValidations.validateUniqueId(uniquePathId);
        logger.info("Setting current video ID {} for user: {}", videoDto.getId(), uniqueCode);
        VideoDto updatedVideo = videoService.updateCurrentVideo(videoDto, uniqueCode);
        return ResponseEntity.ok(new Response<>(true, updatedVideo, 200));
    }
    
    @GetMapping("/{uniquePathId}/{uniqueCode}/getAllVideos")
    public ResponseEntity<Response<List<VideoDto>>> getAllVideos(@PathVariable Integer uniquePathId, @PathVariable String uniqueCode) {
        universalValidations.validateUniqueId(uniquePathId);
        logger.info("Get Videos for user unique code: {}", uniqueCode);
        List<VideoDto> videoDtos = videoService.getAllVideoByUser(uniqueCode);
        return ResponseEntity.ok(new Response<>(true, videoDtos, 200));
    }
    
    @GetMapping("/{uniquePathId}/{uniqueCode}/getCurrentVideo")
    public ResponseEntity<Response<VideoDto>> getCurrentVideo(@PathVariable Integer uniquePathId, @PathVariable String uniqueCode) {
        universalValidations.validateUniqueId(uniquePathId);
        logger.info("Get Videos for user unique code: {}", uniqueCode);
        VideoDto currentvideoDto = videoService.getCurrentVideoByUser(uniqueCode);
        return ResponseEntity.ok(new Response<>(true, currentvideoDto, 200));
    }

    @GetMapping("/{uniquePathId}/{uniqueCode}/count")
    public ResponseEntity<Response<Long>> countVideos(@PathVariable Integer uniquePathId, @PathVariable String uniqueCode) {
        universalValidations.validateUniqueId(uniquePathId);
        logger.info("Counting videos for user unique code: {}", uniqueCode);
        long count = videoService.countVideosByUser(uniqueCode);
        return ResponseEntity.ok(new Response<>(true, count, 200));
    }
}
