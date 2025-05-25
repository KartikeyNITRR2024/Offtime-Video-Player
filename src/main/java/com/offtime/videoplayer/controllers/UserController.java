package com.offtime.videoplayer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.offtime.videoplayer.dtos.UserDto;
import com.offtime.videoplayer.pojos.Response;
import com.offtime.videoplayer.services.UserService;
import com.offtime.videoplayer.validations.UniversalValidations;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UniversalValidations universalValidations;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{uniqueId}/exists/{uniqueCode}")
    public ResponseEntity<Response<Boolean>> checkUniqueCode(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable String uniqueCode) {
        universalValidations.validateUniqueId(pathUniqueId);
        logger.info("Status check for uniqueId {}: Found", pathUniqueId);
        boolean exists = userService.isUniqueCodePresent(uniqueCode);
        Response<Boolean> response = new Response<>(true, exists, 202);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{uniqueId}/createOrUpdate")
    public ResponseEntity<Response<UserDto>> createOrUpdateUser(@PathVariable("uniqueId") Integer pathUniqueId, @RequestBody UserDto userDto) {
        universalValidations.validateUniqueId(pathUniqueId);
        logger.info("User is  {}", userDto);
        UserDto savedUser = userService.createOrUpdateUser(userDto);
        Response<UserDto> response = new Response<>(true, savedUser, 200);
        return ResponseEntity.ok(response);
    }
}
