package com.firomsa.movieforum.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String movieId;
    private String userId;
    private String parentId;
    private String comment;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<String> likes;
    private List<String> dislikes;
    public Comment(String movieId, String userId, String comment, LocalDateTime created, LocalDateTime updated) {
        this.movieId = movieId;
        this.userId = userId;
        this.comment = comment;
        this.created = created;
        this.updated = updated;
    }
    public Comment(String movieId, String userId, String parentId, String comment, LocalDateTime created,
            LocalDateTime updated) {
        this.movieId = movieId;
        this.userId = userId;
        this.parentId = parentId;
        this.comment = comment;
        this.created = created;
        this.updated = updated;
    }
    
}
