package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.ReviewRepository;
import learn.hashbrown_hashers.models.Review;
import org.springframework.stereotype.Service;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;


@Service
public class ReviewService {
    private final ReviewRepository reviewRepo;

    public ReviewService(ReviewRepository reviewRepository) {
        reviewRepo = reviewRepository;
    }

    /**
     * Finds all the possible reviews in the Database
     * @return a list of reviews.
     */
    public List<Review> findAll() {
        return reviewRepo.findAll();
    }
    
    public List<Review> findByRecipeId(int recipeId) {
        if (recipeId <= 0) {
            return null;
        }
        return reviewRepo.findByRecipeId(recipeId);
    }

    /**
     * finds reviews tied to a specific user.
     * @param userId - user we are looking for.
     * @return - returns reviews from user.
     */
    public List<Review> findByUserId(int userId) {
        if (userId <= 0) {
            return null;
        }
        return reviewRepo.findByUserId(userId);
    }

    /**
     * adds a review to the database. checks duplicates and validates
     * the review, seeing if all the required fields are there.
     * @param review - review to be added.
     * @return - returns result of adding a review
     */
    public Result<Review> add(Review review) {
        Result<Review> result = checker(review);
        if (!result.isSuccess()) {
            return result;
        }
        List<Review> duplicate = reviewRepo.findByRecipeId(review.getRecipeId());
        if(duplicate.stream().anyMatch(review1 -> review1.getUserId() == review.getUserId())) {
            result.addMessage("Sorry, you already posted a review here.", ResultType.INVALID);
            return result;
        }
        Review rev = reviewRepo.add(review);
        if(rev == null) {
            result.addMessage("Sorry, your review could not be added.", ResultType.INVALID);
        } else {
            result.setPayload(rev);
        }
        return result;
    }

    public boolean deleteById(int reviewId) {
        if (reviewId <= 0) {
            return false;
        }
        return reviewRepo.deleteById(reviewId);
    }

    private Result<Review> checker(Review review) {
        Result<Review> result = new Result<>();
        if (review == null) {
            result.addMessage("review cannot be empty.", ResultType.INVALID);
            return result;
        }
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()){
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Review>> validation = validator.validate(review);

            validation.forEach(v -> result.addMessage(v.getMessage(), ResultType.INVALID));
        }
        return result;
    }

}
