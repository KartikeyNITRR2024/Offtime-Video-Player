package com.offtime.videoplayer.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "ovp_usertable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uniquecode", nullable = false, unique = true)
    private String uniqueCode;

    @Column(name = "lastuseddatetime")
    private LocalDateTime lastUsedDateTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos;

    @OneToOne
    @JoinColumn(name = "currentvideoid")
    private Video currentVideo;

    public void setCurrentVideo(Video video) {
        this.currentVideo = video;
        if (video != null && (videos == null || !videos.contains(video))) {
            video.setUser(this);
            this.videos.add(video);
        }
    }
}

