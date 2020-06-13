package com.sport.cricket.cricketapi.controller;

import com.sport.cricket.cricketapi.domain.response.Greeting;
import com.sport.cricket.cricketapi.domain.response.User;
import com.sport.cricket.cricketapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
        //(origins = "http://www.cricket46.com")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> users() {
        return userService.getUsers();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticateUser(@RequestBody User user) {
        System.out.println("user--"+user);

        boolean userLoggedIn = userService.authenticateUser(user.getUserName(), user.getPassword());
        System.out.println("userLoggedIn--"+userLoggedIn);
        return new ResponseEntity<Boolean>(userLoggedIn, HttpStatus.OK);
    }

    @GetMapping("/users/{userName}")
    public User user(@PathVariable String userName) {
        return userService.getUser(userName);
    }

    @DeleteMapping("/users/{userName}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userName){
        if(userService.deleteUser(userName)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/users/{userName}")
    public ResponseEntity<User> updateUser(@PathVariable String userName, @RequestBody User user){

        userService.updateUser(userName, user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody User user){
        User createdUser = userService.addUser(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userName}").buildAndExpand(createdUser.getUserName())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
