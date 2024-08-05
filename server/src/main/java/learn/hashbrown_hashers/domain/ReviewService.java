package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.ReviewRepository;
import learn.hashbrown_hashers.data.ReviewTemplateRepository;
import learn.hashbrown_hashers.models.Review;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class ReviewService {
    private final ReviewRepository reviewRepo;

    public ReviewService(ReviewTemplateRepository reviewRepository) {
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

    public List<Review> findByUserId(int userId) {
        if (userId <= 0) {
            return null;
        }
        return reviewRepo.findByUserId(userId);
    }

    public Review add(Review review) {
        Result<Review> result = validate(review);
    }

    private Result<Review> validate(Review review) {
        Result<Review> validation = new Result<Review>();

        if(review == null) {
            validation.addMessage("Review Cannot be null.", ResultType.INVALID);
        } else {

        }
        return validation;
    }

}
