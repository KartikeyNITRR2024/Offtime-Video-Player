package com.offtime.videoplayer.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ovp_configtable")
public class ConfigTable {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column(name = "urllengthmaxlength", nullable = false)
	private Integer urlLengthMaxLength;
    
    @Column(name = "urllengthminlength", nullable = false)
	private Integer urlLengthMinLength;
    
    @Column(name = "maxtimeofinactivityinseconds", nullable = false)
	private Integer maxTimeOfInactivityInSeconds;
    
    @Column(name = "maxusers", nullable = false)
	private Integer maxUsers;
    
    @Column(name = "maxvideosperuser", nullable = false)
	private Integer maxVideosPerUser;
}
