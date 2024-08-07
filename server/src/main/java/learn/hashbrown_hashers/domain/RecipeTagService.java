package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.RecipeTagRepository;
import learn.hashbrown_hashers.models.RecipeTag;

import java.util.Collections;
import java.util.List;

public class RecipeTagService {

    private final RecipeTagRepository recipeTagRepo;

    public RecipeTagService(RecipeTagRepository recipeTagRepository) {
        recipeTagRepo = recipeTagRepository;
    }

    public List<RecipeTag> findbyRecipeId(int recipeId) {
        if(recipeId <= 0) {
            return Collections.emptyList();
        }
        return recipeTagRepo.findbyRecipeId(recipeId);
    }

    public Result<RecipeTag> add(RecipeTag recipeTag) {
        Result<RecipeTag> result = new Result<>();
        if (recipeTag == null) {
            result.addMessage("RecipeTag cannot be null.", ResultType.INVALID);
            return result;
        }
        if(recipeTag.getRecipeId() <= 0 || recipeTag.getTagId() <= 0) {
            result.addMessage("RecipeTag RecipeId or TagId is invalid.", ResultType.INVALID);
            return result;
        }
        result.setPayload(recipeTagRepo.add(recipeTag));
        return result;
    }
}
