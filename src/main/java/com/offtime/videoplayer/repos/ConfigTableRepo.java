package com.offtime.videoplayer.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.offtime.videoplayer.entities.ConfigTable;

@Repository
public interface ConfigTableRepo extends JpaRepository<ConfigTable, Long> {
}