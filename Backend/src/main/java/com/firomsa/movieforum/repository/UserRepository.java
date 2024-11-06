package com.firomsa.movieforum.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.firomsa.movieforum.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    //search using email
    public Optional<User> findByEmail(String email);
}
