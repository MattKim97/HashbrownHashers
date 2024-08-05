package learn.hashbrown_hashers.data;


import learn.hashbrown_hashers.models.Review;

import java.util.List;

public interface ReviewRepository {

    List<Review> findAll();

    List<Review> findByRecipeId(int recipeId);

    List<Review> findByUserId(int userId);






}
