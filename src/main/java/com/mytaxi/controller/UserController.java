package com.mytaxi.controller;

import com.mytaxi.domainobject.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController
{

    private List<User> getUsers()
    {
        User user = new User();
        user.setId("1");
        user.setName("test user1");
        User user1 = new User();
        user1.setId("2");
        user1.setName("test user2");
        User user2 = new User();
        user2.setId("3");
        user2.setName("test user3");
        return Arrays.asList(user, user1, user2);
    }


    @GetMapping("/user")
    public ResponseEntity<List<User>> listUser()
    {
        return new ResponseEntity<List<User>>(getUsers(), HttpStatus.OK);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<User> listUser(@RequestParam String id)
    {
        return new ResponseEntity<User>(getUsers().stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null), HttpStatus.OK);
    }


    @PostMapping("/user")
    public ResponseEntity listUser(@RequestBody User user)
    {
        return new ResponseEntity("18", HttpStatus.OK);
    }

}
