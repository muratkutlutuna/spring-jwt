package com.tpe.controller;

import com.tpe.dto.RegisterRequest;
import com.tpe.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserJwtController {

    @Autowired
    private UserService userService;
    /**
     * Method to register new user
     */
    @PostMapping("/reister")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);
        String message = "User has been registered successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    /**
     * method to login
     */
    @PostMapping("/login")
    public ResponseEntity<String>login(@Valid )
}
