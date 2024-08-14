package com.firomsa.movieforum.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.firomsa.movieforum.model.Review;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

}
