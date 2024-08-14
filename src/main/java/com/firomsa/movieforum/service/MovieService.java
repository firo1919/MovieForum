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
        return movieRepository.save(movie);
    }

    public int deleteMovie(ObjectId id){
        try{
            Movie movie = movieRepository.findById(id).get();
            movieRepository.delete(movie);
            return 1;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
