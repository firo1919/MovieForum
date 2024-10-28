package com.firomsa.movieforum.service;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firomsa.movieforum.model.Movie;
import com.firomsa.movieforum.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    
    public List<Movie> findAllMovies(){
        return movieRepository.findAll();
    }

    public Movie findMovie(String imdbId){
        return movieRepository.findMovieByImdbId(imdbId);
    }

    public Movie addMovie(Movie movie){
        return movieRepository.insert(movie);
    }

    public int deleteMovie(String imdbId){
        try{
            Movie movie = movieRepository.findMovieByImdbId(imdbId);
            movieRepository.delete(movie);
            return 1;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public Movie updateMovie(Movie movie){
        return movieRepository.save(movie);
    }
}
