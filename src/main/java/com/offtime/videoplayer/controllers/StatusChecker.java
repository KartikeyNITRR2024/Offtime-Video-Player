package com.offtime.videoplayer.controllers;

import com.offtime.videoplayer.pojos.Response;
import com.offtime.videoplayer.validations.UniversalValidations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statuschecker")
public class StatusChecker {

    private static final Logger logger = LoggerFactory.getLogger(StatusChecker.class);

    @Autowired
    UniversalValidations UniversalValidations;

    @GetMapping("/{uniqueId}")
    public ResponseEntity<Response<Boolean>> getStatus(@PathVariable("uniqueId") Integer pathUniqueId) {
        UniversalValidations.validateUniqueId(pathUniqueId);
        Response<Boolean> response = new Response<>(true, Boolean.TRUE, 200);
        return ResponseEntity.ok(response);
    }
}
