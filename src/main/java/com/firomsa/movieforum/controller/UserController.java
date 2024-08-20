package com.firomsa.movieforum.controller;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
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

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return new ResponseEntity<>(userService.findUser(new ObjectId(id)),HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.findUserByEMail(email),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody Map<String, String> payload) {
        String firsName = payload.get("firstname");
        String lastName = payload.get("lastname");
        String username = payload.get("username");
        String email = payload.get("email");
        User user = new User(firsName, lastName, username, email);
        return new ResponseEntity<>(userService.addUser(user),HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody Map<String, String> payload) {
        User existingUser = userService.findUser(new ObjectId(userId));
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingUser.setFirsName(payload.get("firstname"));
        existingUser.setLastName(payload.get("lastname"));
        existingUser.setUsername(payload.get("username"));
        existingUser.setEmail(payload.get("email"));
        User updatedUser = userService.updateUser(existingUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId){
        int result = userService.deleteUser(new ObjectId(userId));
        if (result == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/watchList")
    public ResponseEntity<User> deleteWatchList(@RequestParam String imdbId, @RequestParam String userId){
        return new ResponseEntity<>(userService.removeWatchList(imdbId, userId),HttpStatus.OK);
    }

    @PostMapping("/watchList")
    public ResponseEntity<User> addWatchList(@RequestParam String imdbId, @RequestParam String userId){
        return new ResponseEntity<>(userService.addToWatchList(imdbId, userId),HttpStatus.OK);
    }
}
