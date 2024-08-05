package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.ReviewRepository;
import learn.hashbrown_hashers.data.ReviewTemplateRepository;
import learn.hashbrown_hashers.models.Review;
import org.springframework.stereotype.Service;


import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;


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

    public Result<Review> add(Review review) {
        Result<Review> result = checker(review);
        return result;
    }

    private Result<Review> checker(Review review) {
        Result<Review> result = new Result<>();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()){
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Review>> validation = validator.validate(review);

            validation.forEach(v -> result.addMessage(v.getMessage(), ResultType.INVALID));
        }
        return result;
    }

}
