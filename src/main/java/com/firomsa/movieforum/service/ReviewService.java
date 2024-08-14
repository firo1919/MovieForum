package com.firomsa.movieforum.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.firomsa.movieforum.model.Movie;
import com.firomsa.movieforum.model.Review;
import com.firomsa.movieforum.model.User;
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

    public Review addReview(String imdbId, Review review, ObjectId userId){
        Review addedReview = reviewRepository.save(review);
        mongoTemplate.update(Movie.class)
                     .matching(Criteria.where("imdbId").is(imdbId))
                     .apply(new Update().push("reviewIds").value(addedReview.getId()))
                     .first();   
        mongoTemplate.update(User.class)
                     .matching(Criteria.where("userId").is(userId))
                     .apply(new Update().push("reviewIds").value(addedReview.getId()))
                     .first();                
        return addedReview;
    }

    public int deleteReview(Review review){
        ObjectId reviewId = review.getId();
    
        // Remove review from the movie collection
        mongoTemplate.updateMulti(new Query(Criteria.where("reviewIds").is(reviewId)),
                new Update().pull("reviewIds", reviewId), Movie.class);
        
        // Remove review from the users collection
        mongoTemplate.updateMulti(new Query(Criteria.where("reviewIds").is(reviewId)),
                new Update().pull("reviewIds", reviewId), User.class);
        
        // Delete the review from the review collection
        reviewRepository.delete(review);
        
        return 1; // Return 1 to indicate successful deletion
                     

    }
}
