package com.firomsa.movieforum.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.firomsa.movieforum.model.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {
    public Movie findMovieByImdbId(String imdbId);
}
