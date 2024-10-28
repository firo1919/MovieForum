package com.firomsa.movieforum.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.firomsa.movieforum.model.User;
import com.firomsa.movieforum.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.findAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            return new ResponseEntity<>(userService.findUserByEMail(email),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody Map<String, String> payload) {
        String firsName = payload.get("firstName");
        String lastName = payload.get("lastName");
        String username = payload.get("userName");
        String password = payload.get("password");
        String email = payload.get("email");
        User user = new User(firsName, lastName, username, password,email);
        try {
            return new ResponseEntity<>(userService.addUser(user),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody Map<String, String> payload) {
        User existingUser = userService.findUserByEMail(email);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingUser.setFirsName(payload.get("firstName"));
        existingUser.setLastName(payload.get("lastName"));
        existingUser.setUserName(payload.get("userName"));
        existingUser.setPassword(payload.get("password"));
        existingUser.setEmail(payload.get("email"));
        User updatedUser = userService.updateUser(existingUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<User> deleteUser(@PathVariable String email){
        int result = userService.deleteUser(email);
        if (result == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/watchList")
    public ResponseEntity<User> deleteWatchList(@RequestParam String imdbId, @RequestParam String email){
        return new ResponseEntity<>(userService.removeWatchList(imdbId, email),HttpStatus.OK);
    }

    @PostMapping("/watchList")
    public ResponseEntity<User> addWatchList(@RequestParam String imdbId, @RequestParam String email){
        return new ResponseEntity<>(userService.addToWatchList(imdbId, email),HttpStatus.OK);
    }
}
