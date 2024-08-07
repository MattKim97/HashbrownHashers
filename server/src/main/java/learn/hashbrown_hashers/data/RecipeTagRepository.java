package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.RecipeTag;


import java.util.List;

public interface RecipeTagRepository {

    List<RecipeTag> findbyRecipeId(int recipeId);


    RecipeTag add(RecipeTag recipeTag);
}
