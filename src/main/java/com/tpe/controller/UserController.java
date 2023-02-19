package com.tpe.controller;

import com.tpe.dto.UserRequest;
import com.tpe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;// create UserService First then inject .

    @PostMapping

    @RequestMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequest userRequest){
        // create Request DTO
        userService.saveUser(userRequest);//

        String response="User has been registered successfully";
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
