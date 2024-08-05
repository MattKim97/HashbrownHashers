package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.data.mappers.ReviewMapper;
import learn.hashbrown_hashers.models.Review;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ReviewTemplateRepository implements ReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReviewTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Review> findAll() {
        final String sql = "select review, user_id, recipe_id, review_title, review_desc, rating "
                + "from reviews limit 1000;";
        return jdbcTemplate.query(sql, new ReviewMapper());

    }

    @Override
    public List<Review> findByRecipeId(int recipeId) {
        final String sql = "select review, user_id, recipe_id, review_title, review_desc, rating "
                + "from reviews "
                + "where recipe_id = ?;";
        return jdbcTemplate.query(sql, new ReviewMapper(),recipeId);
    }

    @Override
    public List<Review> findByUserId(int userId) {
        final String sql = "select review, user_id, recipe_id, review_title, review_desc, rating "
                + "from reviews "
                + "where user_id = ?;";
        return jdbcTemplate.query(sql, new ReviewMapper(),userId);
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