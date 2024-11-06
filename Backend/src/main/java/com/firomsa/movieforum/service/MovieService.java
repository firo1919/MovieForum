package com.firomsa.movieforum.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firomsa.movieforum.model.Movie;
import com.firomsa.movieforum.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;
    
    public List<Movie> findAllMovies(){
        return repository.findAll();
    }

    public Optional<Movie> findMovie(String id){
        return repository.findById(id);
    }

    public Movie addMovie(Movie movie){
        return repository.insert(movie);
    }

    public void deleteMovie(String id){
        repository.deleteById(id);
    }
    
    public Movie updateMovie(Movie movie){
        return repository.save(movie);
    }
}
