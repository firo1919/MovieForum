package com.firomsa.movieforum.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.firomsa.movieforum.model.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    public Optional<List<Comment>> findByUserId(String userId);
    public Optional<List<Comment>> findByParentId(String parentId);
    public Optional<List<Comment>> findByMovieId(String movieId);
    public Optional<List<Comment>> findByMovieIdAndParentIdIsNull(String movieId);
}
