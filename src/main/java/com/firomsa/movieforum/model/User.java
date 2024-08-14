package com.firomsa.movieforum.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "users")
public class User {
    @Id
    private ObjectId userId;
    private String username;
    private String email;
    @DocumentReference
    private List<Review> reviews;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
}
