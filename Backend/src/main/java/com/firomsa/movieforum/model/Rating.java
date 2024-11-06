package com.firomsa.movieforum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Rating {
    private String userId;
    private int rating;
}
