package com.firomsa.movieforum.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;

import com.firomsa.movieforum.model.Movie;
import com.firomsa.movieforum.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies() {
        try {
            return new ResponseEntity<>(movieService.findAllMovies(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Movie> getMovie(@PathVariable String imdbId) {
        try {
            return new ResponseEntity<>(movieService.findMovie(imdbId),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Map<String, Object> payload) {
        String imdbId = payload.get("imdbId").toString();
        String title = payload.get("title").toString();
        String releaseDate = payload.get("releaseDate").toString();
        String trailerLink = payload.get("trailerLink").toString();
        List<String> genres =  (List<String>) payload.get("genres");
        String poster = payload.get("poster").toString();
        List<String> backDrops = (List<String>) payload.get("backdrops");
        Movie movie = new Movie(imdbId, title, releaseDate, trailerLink, genres, poster, backDrops);
        return new ResponseEntity<>(movieService.addMovie(movie),HttpStatus.CREATED);
    }

    @SuppressWarnings("unchecked")
    @PutMapping("/{imdbId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String imdbId, @RequestBody Map<String, Object> payload) {
        Movie existingMovie = movieService.findMovie(imdbId);
        if (existingMovie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        existingMovie.setTitle(payload.get("title").toString());
        existingMovie.setReleaseDate(payload.get("releaseDate").toString());
        existingMovie.setTrailerLink(payload.get("trailerLink").toString());
        existingMovie.setGenres((List<String>) payload.get("genres"));
        existingMovie.setPoster(payload.get("poster").toString());
        existingMovie.setBackDrops((List<String>) payload.get("backdrops"));
        
        Movie updatedMovie = movieService.updateMovie(existingMovie);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }

    @DeleteMapping("/{imdbId}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable String imdbId){
        int result = movieService.deleteMovie(imdbId);
        if (result == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
