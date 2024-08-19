package com.firomsa.movieforum.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String firsName;
    private String lastName;
    private String username;
    @Indexed(unique = true)
    private String email;

    public User(String firsName, String lastName, String username, String email) {
        this.firsName = firsName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }
    
}
