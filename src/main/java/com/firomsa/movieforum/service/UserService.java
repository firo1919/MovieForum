package com.firomsa.movieforum.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.firomsa.movieforum.model.Movie;
import com.firomsa.movieforum.model.User;
import com.firomsa.movieforum.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieService movieService;
    
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    public User findUserByEMail(String email){
        return userRepository.findByEmail(email);
    }
    public User findUser(ObjectId userId){
        return userRepository.findById(userId).get();
    }
    public User addUser(User user){
        return userRepository.insert(user);
    }
    public int deleteUser(ObjectId id){
        try{
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
            return 1;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public User updateUser(User user){
        return userRepository.save(user);
    }
    
    public User addToWatchList(String imdbId, String userId){
        User existingUser = findUser(new ObjectId(userId));
        Movie movie = movieService.findMovie(imdbId);
        if(!existingUser.getWatchList().contains(movie)){
            existingUser.getWatchList().add(movie);
            return updateUser(existingUser);
        }
        return existingUser;
    }

    public User removeWatchList(String imdbId, String userId){
        User existingUser = findUser(new ObjectId(userId));
        Movie movie = movieService.findMovie(imdbId);
        existingUser.getWatchList().remove(movie);
        return updateUser(existingUser);
    }
    
}
