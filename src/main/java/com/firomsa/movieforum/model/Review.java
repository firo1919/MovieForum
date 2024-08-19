package com.firomsa.movieforum.model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
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
@Document(collection = "reviews")
public class Review {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private String comment;
    private int rating;
    private LocalDateTime created;
    private LocalDateTime updated;
    
    public Review(ObjectId userId, String comment, int rating, LocalDateTime created, LocalDateTime updated) {
        this.userId = userId;
        this.comment = comment;
        this.rating = rating;
        this.created = created;
        this.updated = updated;
    }  
}
