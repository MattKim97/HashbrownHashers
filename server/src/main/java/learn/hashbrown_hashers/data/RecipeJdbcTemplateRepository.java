package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.data.mappers.RecipeMapper;
import learn.hashbrown_hashers.data.mappers.TagMapper;
import learn.hashbrown_hashers.models.Recipe;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import learn.hashbrown_hashers.models.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class RecipeJdbcTemplateRepository implements RecipeRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecipeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Recipe> findAllRecipes(){
        final String sql = "select * "
                + "from recipes limit 1000";
        return jdbcTemplate.query(sql,new RecipeMapper());
    }

    @Override
    @Transactional
    public Recipe findById(int recipeId){
        final String sql = "select * "
                + "from recipes "
                + "where recipe_id = ?;";
        Recipe recipe = jdbcTemplate.query(sql, new RecipeMapper(),recipeId).stream()
                .findFirst().orElse(null);

        if (recipe != null){
            addTags(recipe);
        }

        return recipe;
    }

    @Override
    @Transactional
    public Recipe add(Recipe recipe){

        final String sql = "insert into recipes (recipe_name, difficulty, spiciness, prep_time, image_link, recipe_desc, recipe_text, user_id) "
                + "values(?,?,?,?,?,?,?,?) ;";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,recipe.getRecipeName());
            ps.setInt(2,recipe.getDifficulty());
            ps.setInt(3,recipe.getSpicyness());
            ps.setInt(4,recipe.getPrepTime());
            ps.setString(5,recipe.getImageUrl());
            ps.setString(6,recipe.getDescription());
            ps.setString(7,recipe.getText());
            ps.setInt(8,recipe.getUserId());

            return ps;
        }, keyHolder);
        if (rowsAffected <= 0){
            return null;
        }

        recipe.setRecipeId(keyHolder.getKey().intValue());
        recipe.setTimePosted(LocalDate.now());
        recipe.setTimeUpdated(LocalDate.now());

        return recipe;
    }

    @Override
    @Transactional
    public boolean update(Recipe recipe){

        final String sql = "update recipes set "
                + "recipe_name = ?, "
                + "difficulty = ?, "
                + "spiciness = ?, "
                + "prep_time = ?, "
                + "image_link = ?, "
                + "recipe_desc = ?, "
                + "recipe_text = ? "
                + "where recipe_id = ? ;";

        boolean updated = jdbcTemplate.update(sql,
                recipe.getRecipeName(),
                recipe.getDifficulty(),
                recipe.getSpicyness(),
                recipe.getPrepTime(),
                recipe.getImageUrl(),
                recipe.getDescription(),
                recipe.getText(),
                recipe.getRecipeId()
                ) > 0;

        if(updated){
            recipe.setTimeUpdated(LocalDate.now());
        }

        return updated;
    }


    @Override
    @Transactional
    public boolean deleteById(int recipeId) {
       return jdbcTemplate.update("delete from recipes where recipe_id = ?;",recipeId) > 0;
    }


    private void addTags(Recipe recipe) {
        final String sql = "SELECT t.tag_id, t.tag_name " +
                "FROM recipe_tags rt " +
                "INNER JOIN tags t ON rt.tag_id = t.tag_id " +
                "WHERE rt.recipe_id = ?;";

        List<Tag> recipeTags = jdbcTemplate.query(sql, new TagMapper(), recipe.getRecipeId());
        recipe.setTags(recipeTags);
    }



}
