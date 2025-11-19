package com.example.workhub.controller;

import com.example.workhub.model.Users;
import com.example.workhub.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    @Autowired
    UsersRepo repo ;

    @PostMapping("/addUser")
    public void addUser(@RequestBody Users user) {
        repo.save(user);
    }
}
