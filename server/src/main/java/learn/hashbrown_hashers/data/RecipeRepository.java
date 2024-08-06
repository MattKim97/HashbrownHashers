package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.Recipe;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecipeRepository {
    List<Recipe> findAllRecipes();

    @Transactional
    Recipe findById(int recipeId);

    @Transactional
    List<Recipe> findByUserId(int userId);

    @Transactional
    List<Recipe> findByText(String text);

    @Transactional
    Recipe add(Recipe recipe);

    @Transactional
    boolean update(Recipe recipe);

    @Transactional
    boolean deleteById(int recipeId);
}
