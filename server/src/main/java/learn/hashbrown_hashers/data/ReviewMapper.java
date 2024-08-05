package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Review> {


    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review = new Review();
        review.setReviewId(resultSet.getInt("review_id"));
        review.setUserId(resultSet.getInt("user_id"));
        review.setRecipeId(resultSet.getInt("recipe_id"));
        review.setTitle(resultSet.getString("review_title"));
        review.setDescription(resultSet.getString("review_desc"));
        review.setRating(resultSet.getInt("rating"));
        return review;
    }
}
