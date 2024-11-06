package com.firomsa.movieforum.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "movies")
public class Movie {
    @Id
    private String id;
    private String imdbId;
    private String title;
    private String description;
    private String releaseDate;
    private String trailerLink;
    private String poster;
    private List<String> genres;
    private List<Rating> ratings;
    private List<String> backDrops;
    private List<String> likes;
    private List<String> dislikes;
    private List<String> favoriteBy;
    
    public Movie(String imdbId, String title, String description, String releaseDate, String trailerLink, String poster,
            List<String> genres, List<String> backDrops) {
        this.imdbId = imdbId;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.trailerLink = trailerLink;
        this.poster = poster;
        this.genres = genres;
        this.backDrops = backDrops;
    }
    
    
}
