package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.data.mappers.ReviewMapper;
import learn.hashbrown_hashers.models.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


@Repository
public class ReviewTemplateRepository implements ReviewRepository{
    private final JdbcTemplate jdbcTemplate;

    public ReviewTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Review> findAll() {
        final String sql = "select review_id, user_id, recipe_id, review_title, review_desc, rating "
                + "from reviews limit 1000;";
        return jdbcTemplate.query(sql, new ReviewMapper());

    }

    @Override
    public List<Review> findByRecipeId(int recipeId) {
        final String sql = "select review_id, user_id, recipe_id, review_title, review_desc, rating "
                + "from reviews "
                + "where recipe_id = ?;";
        return jdbcTemplate.query(sql, new ReviewMapper(),recipeId);
    }

    @Override
    public List<Review> findByUserId(int userId) {
        final String sql = "select review_id, user_id, recipe_id, review_title, review_desc, rating "
                + "from reviews "
                + "where user_id = ?;";
        return jdbcTemplate.query(sql, new ReviewMapper(),userId);
    }


    @Override
    public Review add(Review review){
        final String sql = "insert into reviews (user_id, recipe_id, review_title, review_desc, rating) "
                + " values (?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getRecipeId());
            ps.setString(3, review.getTitle());
            ps.setString(4, review.getDescription());
            ps.setInt(5, review.getRating());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        review.setReviewId(keyHolder.getKey().intValue());
        return review;
    }

    @Override
    public boolean deleteById(int reviewId){
        return jdbcTemplate.update(
                "delete from reviews where review_id = ?;",
                reviewId) > 0;
    }
}
    /*
create table reviews (
    review_id int not null primary key auto_increment,
    user_id int not null,
    recipe_id int not null,
    review_title varchar(250),
    review_desc varchar(1000),
    rating tinyint(1) not null,
    constraint fk_review_user_id
        foreign key (user_id)
        references recipe_users(user_id),
    constraint fk_review_recipe_id
        foreign key (recipe_id)
        references recipes(recipe_id)
);
     */