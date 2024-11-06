package com.firomsa.movieforum.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firomsa.movieforum.model.Comment;
import com.firomsa.movieforum.model.User;
import com.firomsa.movieforum.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;
    @Autowired
    private UserService userService;

    public List<Comment> findAllComments() {
        return repository.findAll();
    }

    public Optional<List<Comment>> findAllCommentsByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public Optional<List<Comment>> findAllCommentsByMovieId(String movieId) {
        return repository.findByMovieId(movieId);
    }

    public Optional<List<Comment>> findAllInitialComments(String movieId) {
        return repository.findByMovieIdAndParentIdIsNull(movieId);
    }

    public Optional<List<Comment>> findAllCommentsWithParent(String parentId) {
        return repository.findByParentId(parentId);
    }

    public Optional<Comment> findComment(String id) {
        return repository.findById(id);
    }

    public Comment addComment(Comment comment) {
        comment.setCreated(LocalDateTime.now());
        return repository.insert(comment);
    }

    public void deleteComment(String id) {
        repository.deleteById(id);
    }

    public Comment updateComment(Comment comment) {
        comment.setUpdated(LocalDateTime.now());
        return repository.save(comment);
    }

    public Optional<Comment> addLikedComment(String userId, String commentId) {
        Optional<User> user = userService.findUser(userId);
        Optional<Comment> comment = repository.findById(commentId);
        if (user.isPresent() && comment.isPresent()) {
            Comment existingComment = comment.get();
            if (existingComment.getLikes() != null) {
                existingComment.getLikes().add(userId);
            } else {
                existingComment.setLikes(new ArrayList<>(List.of(userId)));
            }
            return Optional.of(repository.save(existingComment));
        }
        return Optional.empty();
    }

    public Optional<Comment> removeLikedComment(String userId, String commentId) {
        Optional<User> user = userService.findUser(userId);
        Optional<Comment> comment = repository.findById(commentId);
        if (user.isPresent() && comment.isPresent()) {
            Comment existingComment = comment.get();
            if (existingComment.getLikes() != null) {
                existingComment.getLikes().remove(userId);
            } 
            return Optional.of(repository.save(existingComment));
        }
        return Optional.empty();
    }

    public Optional<Comment> addDislikedComment(String userId, String commentId) {
        Optional<User> user = userService.findUser(userId);
        Optional<Comment> comment = repository.findById(commentId);
        if (user.isPresent() && comment.isPresent()) {
            Comment existingComment = comment.get();
            if (existingComment.getDislikes() != null) {
                existingComment.getDislikes().add(userId);
            } else {
                existingComment.setDislikes(new ArrayList<>(List.of(userId)));
            }
            return Optional.of(repository.save(existingComment));
        }
        return Optional.empty();
    }

    public Optional<Comment> removeDislikedComment(String userId, String commentId) {
        Optional<User> user = userService.findUser(userId);
        Optional<Comment> comment = repository.findById(commentId);
        if (user.isPresent() && comment.isPresent()) {
            Comment existingComment = comment.get();
            if (existingComment.getDislikes() != null) {
                existingComment.getDislikes().remove(userId);
            }
            return Optional.of(repository.save(existingComment));
        }
        return Optional.empty();
    }
}
