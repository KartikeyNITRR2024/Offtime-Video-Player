package com.offtime.videoplayer.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.offtime.videoplayer.entities.Video;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByUserId(Long userId);
    long countByUserId(Long userId);
    void deleteByUserId(Long userId);
    Optional<Video> findByVideoUrlAndUserId(String videoUrl, Long userId);
}

