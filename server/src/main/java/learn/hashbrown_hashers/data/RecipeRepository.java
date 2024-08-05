package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.Recipe;

import java.util.List;

public interface RecipeRepository {
    List<Recipe> findAllRecipes();

    Recipe findById(int recipeId);

    Recipe add(Recipe recipe);

    boolean update(Recipe recipe);

    boolean deleteById(int recipeId);
}
