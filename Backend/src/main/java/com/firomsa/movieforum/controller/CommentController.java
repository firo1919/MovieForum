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

import com.firomsa.movieforum.model.Comment;
import com.firomsa.movieforum.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService service;

    @GetMapping
    public ResponseEntity<List<Comment>> getComments() {
        return new ResponseEntity<>(service.findAllComments(),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable String userId) {
        Optional<List<Comment>> comments =  service.findAllCommentsByUserId(userId);
        if (comments.isPresent()) {
            return new ResponseEntity<>(comments.get(),HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<Comment>> getCommentsByParentId(@PathVariable String parentId) {
        Optional<List<Comment>> comments =  service.findAllCommentsWithParent(parentId);
        if (comments.isPresent()) {
            return new ResponseEntity<>(comments.get(),HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Comment>> getCommentsByMovie(@PathVariable String movieId) {
        Optional<List<Comment>> comments =  service.findAllCommentsByMovieId(movieId);
        if (comments.isPresent()) {
            return new ResponseEntity<>(comments.get(),HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/moviefirst/{movieId}")
    public ResponseEntity<List<Comment>> getFirstLevelComments(@PathVariable String movieId) {
        Optional<List<Comment>> comments =  service.findAllInitialComments(movieId);
        if (comments.isPresent()) {
            return new ResponseEntity<>(comments.get(),HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable String id) {
        Optional<Comment> comment = service.findComment(id);
        if (comment.isPresent()) {
            return new ResponseEntity<>(comment.get(),HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        return new ResponseEntity<>(service.addComment(comment),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable String id, Comment comment) {
        Optional<Comment> existingComment = service.findComment(id);
        if (existingComment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        comment.setId(id);
        Comment updatedComment = service.updateComment(comment);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable String id){
        service.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/{userId}/likes")
    public ResponseEntity<Comment> likeComment(@PathVariable String userId, @RequestParam String CommentId, @RequestParam int type){
        if(type==0){
            Optional<Comment> Comment = service.addLikedComment(userId, CommentId);
            if (Comment.isPresent()) {
                return new ResponseEntity<>(Comment.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Comment> Comment = service.removeLikedComment(userId, CommentId);
        if (Comment.isPresent()) {
            return new ResponseEntity<>(Comment.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    }
    @PostMapping("/{userId}/dislikes")
    public ResponseEntity<Comment> dislikeComment(@PathVariable String userId, @RequestParam String CommentId, @RequestParam int type){
        if (type==0) {
            Optional<Comment> Comment = service.addDislikedComment(userId, CommentId);
            if (Comment.isPresent()) {
                return new ResponseEntity<>(Comment.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Comment> Comment = service.removeDislikedComment(userId, CommentId);
        if (Comment.isPresent()) {
            return new ResponseEntity<>(Comment.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    }
}
