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

import com.firomsa.movieforum.model.Movie;
import com.firomsa.movieforum.model.Rating;
import com.firomsa.movieforum.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies() {
        return new ResponseEntity<>(service.findAllMovies(),HttpStatus.OK);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable String movieId) {
        Optional<Movie> movie = service.findMovie(movieId);
        if(movie.isPresent()){
            return new ResponseEntity<>(movie.get(),HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(service.addMovie(movie),HttpStatus.CREATED);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String movieId, @RequestBody Movie movie) {
        Optional<Movie> existingMovie = service.findMovie(movieId);
        if (existingMovie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        movie.setId(movieId);
        Movie updatedMovie = service.updateMovie(movie);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable String movieId){
        service.deleteMovie(movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{movieId}/rating")
    public ResponseEntity<Movie> addRating(@PathVariable String movieId,@RequestParam String userId,
            @RequestParam int rating){
        Optional<Movie> existingMovie = service.findMovie(movieId);
        if (existingMovie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Movie movie = existingMovie.get();
        movie.getRatings().add(new Rating(userId, rating));
        Movie updatedMovie = service.updateMovie(movie);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }

    @PutMapping("/{movieId}/rating")
    public ResponseEntity<Movie> updateRating(@PathVariable String movieId,@RequestParam String userId,
            @RequestParam int rating){
        Optional<Movie> existingMovie = service.findMovie(movieId);
        if (existingMovie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Movie movie = existingMovie.get();

        List<Rating> ratings = movie.getRatings();
        int n = ratings.size();
        for (int i = 0; i < n; i++) {
            if (ratings.get(i).getUserId().equals(userId)) {
                ratings.get(i).setRating(rating);
            }
        }
        movie.setRatings(ratings);
        movie = service.updateMovie(movie);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}/rating")
    public ResponseEntity<Movie> deleteRating(@PathVariable String movieId,@RequestParam String userId){
        Optional<Movie> existingMovie = service.findMovie(movieId);
        if (existingMovie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Movie movie = existingMovie.get();

        List<Rating> ratings = movie.getRatings();
        int n = ratings.size();
        for (int i = 0; i < n; i++) {
            if (ratings.get(i).getUserId().equals(userId)) {
                ratings.remove(i);
                break;
            }
        }
        movie.setRatings(ratings);
        movie = service.updateMovie(movie);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }
}
