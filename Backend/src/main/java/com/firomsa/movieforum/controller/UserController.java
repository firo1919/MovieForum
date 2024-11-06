package com.firomsa.movieforum.controller;

import java.util.List;
import java.util.Optional;

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

import com.firomsa.movieforum.model.Movie;
import com.firomsa.movieforum.model.User;
import com.firomsa.movieforum.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(service.findAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = service.findUserByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        Optional<User> user = service.findUser(userId);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<>(service.addUser(user),HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user) {
        Optional<User> existingUser = service.findUser(userId);
        if (existingUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setUserId(userId);
        User updatedUser = service.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId){
        service.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}/favorite/{movieId}")
    public ResponseEntity<User> deleteFavorite(@PathVariable String movieId, @PathVariable String userId){
        Optional<User> user = service.removeFromFavorites(movieId, userId);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    }

    @PostMapping("/{userId}/favorite")
    public ResponseEntity<User> addFavorite(@RequestParam String movieId, @PathVariable String userId){
        Optional<User> user = service.addToFavorites(movieId, userId);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userId}/likes")
    public ResponseEntity<Movie> likeMovie(@PathVariable String userId, @RequestParam String movieId, @RequestParam int type){
        if(type==0){
            Optional<Movie> movie = service.addLikedMovie(userId, movieId);
            if (movie.isPresent()) {
                return new ResponseEntity<>(movie.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Movie> movie = service.removeLikedMovie(userId, movieId);
        if (movie.isPresent()) {
            return new ResponseEntity<>(movie.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    }
    @PostMapping("/{userId}/dislikes")
    public ResponseEntity<Movie> dislikeMovie(@PathVariable String userId, @RequestParam String movieId, @RequestParam int type){
        if (type==0) {
            Optional<Movie> movie = service.addDislikedMovie(userId, movieId);
            if (movie.isPresent()) {
                return new ResponseEntity<>(movie.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Movie> movie = service.removeDislikedMovie(userId, movieId);
        if (movie.isPresent()) {
            return new ResponseEntity<>(movie.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    }
    
}
