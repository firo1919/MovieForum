package com.firomsa.movieforum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firomsa.movieforum.model.Movie;
import com.firomsa.movieforum.model.User;
import com.firomsa.movieforum.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private MovieService movieService;

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<User> findUser(String userId) {
        return repository.findById(userId);
    }

    public User addUser(User user) {
        return repository.insert(user);
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    public User updateUser(User user) {
        return repository.save(user);
    }

    public Optional<User> addToFavorites(String movieId, String userId) {
        Optional<User> user = repository.findById(userId);
        Optional<Movie> movie = movieService.findMovie(movieId);
        if (user.isPresent() && movie.isPresent()) {
            User existingUser = user.get();
            Movie existingMovie = movie.get();
            if (existingUser.getFavorites() != null) {
                existingUser.getFavorites().add(movieId);
            } else {
                existingUser.setFavorites(new ArrayList<>(List.of(movieId)));
            }

            if (existingMovie.getFavoriteBy() != null) {
                existingMovie.getFavoriteBy().add(userId);
            } else {
                existingMovie.setFavoriteBy(new ArrayList<>(List.of(userId)));
            }
            movieService.updateMovie(existingMovie);
            repository.save(existingUser);
            user = repository.findById(userId);
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> removeFromFavorites(String movieId, String userId) {
        Optional<User> user = repository.findById(userId);
        Optional<Movie> movie = movieService.findMovie(movieId);
        if (user.isPresent() && movie.isPresent()) {
            User existingUser = user.get();
            Movie existingMovie = movie.get();
            if (existingUser.getFavorites() != null) {
                existingUser.getFavorites().remove(movieId);
            }
            if (existingMovie.getFavoriteBy() != null) {
                existingMovie.getFavoriteBy().remove(userId);
            }
            movieService.updateMovie(existingMovie);
            repository.save(existingUser);
            user = repository.findById(userId);
            return user;
        }
        return Optional.empty();
    }

    public Optional<Movie> addLikedMovie(String userId, String movieId) {
        Optional<User> user = repository.findById(userId);
        Optional<Movie> movie = movieService.findMovie(movieId);
        if (user.isPresent() && movie.isPresent()) {
            Movie existingMovie = movie.get();
            if (existingMovie.getLikes() != null) {
                existingMovie.getLikes().add(userId);
            } else {
                existingMovie.setLikes(new ArrayList<>(List.of(userId)));
            }
            return Optional.of(movieService.updateMovie(existingMovie));
        }
        return Optional.empty();
    }

    public Optional<Movie> removeLikedMovie(String userId, String movieId) {
        Optional<User> user = repository.findById(userId);
        Optional<Movie> movie = movieService.findMovie(movieId);
        if (user.isPresent() && movie.isPresent()) {
            Movie existingMovie = movie.get();
            if (existingMovie.getLikes() != null) {
                existingMovie.getLikes().remove(userId);
            }
            return Optional.of(movieService.updateMovie(existingMovie));
        }
        return Optional.empty();
    }

    public Optional<Movie> addDislikedMovie(String userId, String movieId) {
        Optional<User> user = repository.findById(userId);
        Optional<Movie> movie = movieService.findMovie(movieId);
        if (user.isPresent() && movie.isPresent()) {
            Movie existingMovie = movie.get();
            if (existingMovie.getDislikes() != null) {
                existingMovie.getDislikes().add(userId);
            } else {
                existingMovie.setDislikes(new ArrayList<>(List.of(userId)));
            }
            return Optional.of(movieService.updateMovie(existingMovie));
        }
        return Optional.empty();
    }

    public Optional<Movie> removeDislikedMovie(String userId, String movieId) {
        Optional<User> user = repository.findById(userId);
        Optional<Movie> movie = movieService.findMovie(movieId);
        if (user.isPresent() && movie.isPresent()) {
            Movie existingMovie = movie.get();
            if (existingMovie.getDislikes() != null) {
                existingMovie.getDislikes().remove(userId);
            } 
            return Optional.of(movieService.updateMovie(existingMovie));
        }
        return Optional.empty();
    }

}
