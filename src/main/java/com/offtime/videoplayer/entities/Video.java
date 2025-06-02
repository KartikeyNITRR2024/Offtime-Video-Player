package com.offtime.videoplayer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "user")
@Table(name = "ovp_videotable")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "videoname", nullable = false)
    private String videoName;

    @Column(name = "videourl", nullable = false)
    private String videoUrl;

    @Column(name = "laststoptime", nullable = false)
    private Long lastStopTime;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
}

