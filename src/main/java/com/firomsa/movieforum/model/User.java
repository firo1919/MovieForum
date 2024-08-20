package com.firomsa.movieforum.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
    private String firsName;
    private String lastName;
    private String userName;
    private String password;
    @Indexed(unique = true)
    private String email;
    @DocumentReference
    private List<Movie> watchList;
    public User(String firsName, String lastName, String userName, String password, String email) {
        this.firsName = firsName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    
    
}
