package com.firomsa.movieforum.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.firomsa.movieforum.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    //search using email
    public User findByEmail(String email);
}
