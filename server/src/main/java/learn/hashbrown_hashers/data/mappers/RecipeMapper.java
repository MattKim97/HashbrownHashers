package learn.hashbrown_hashers.data.mappers;

import learn.hashbrown_hashers.models.Recipe;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeMapper implements RowMapper<Recipe> {

    @Override
    public Recipe mapRow(ResultSet resultSet, int i) throws SQLException{

        Recipe recipe = new Recipe();
        recipe.setRecipeId(resultSet.getInt("recipe_id"));
        recipe.setRecipeName(resultSet.getString("recipe_name"));
        recipe.setDifficulty(resultSet.getInt("difficulty"));
        recipe.setSpicyness(resultSet.getInt("spiciness"));
        recipe.setPrepTime(resultSet.getInt("prep_time"));
        recipe.setImageUrl(resultSet.getString("image_link"));
        recipe.setDescription(resultSet.getString("recipe_desc"));
        recipe.setText(resultSet.getString("recipe_text"));
        recipe.setUserId(resultSet.getInt("user_id"));
        recipe.setTimePosted(resultSet.getDate("time_posted").toLocalDate());
        recipe.setTimeUpdated(resultSet.getDate("time_updated").toLocalDate());

        return recipe;
    }
}
