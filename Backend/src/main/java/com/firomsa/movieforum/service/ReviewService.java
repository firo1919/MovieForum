package com.firomsa.movieforum.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.firomsa.movieforum.model.Movie;
import com.firomsa.movieforum.model.Review;
import com.firomsa.movieforum.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository; 
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Review> findAllReviews(){
        return reviewRepository.findAll();
    }

    public List<Review> findAllReviewsByUserId(ObjectId userId){
        return reviewRepository.findAllByUserId(userId);
    }

    public Review findReview(ObjectId id){
        return reviewRepository.findById(id).get();
    }
    
    public Review addReview(String imdbId, Review review){
        Review addedReview = reviewRepository.save(review);
        mongoTemplate.update(Movie.class)
                     .matching(Criteria.where("imdbId").is(imdbId))
                     .apply(new Update().push("reviews").value(addedReview.getId()))
                     .first();            
        return addedReview;
    }

    public int deleteReview(Review review){
        try{
            reviewRepository.delete(review);
            return 1;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public Review updateReview(Review review){
        return reviewRepository.save(review);
    }
}
