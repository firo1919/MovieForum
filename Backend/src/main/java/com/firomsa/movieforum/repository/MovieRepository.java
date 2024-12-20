package com.firomsa.movieforum.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.firomsa.movieforum.model.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

}
