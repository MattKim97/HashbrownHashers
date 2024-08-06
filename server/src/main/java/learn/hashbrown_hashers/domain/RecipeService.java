package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.RecipeRepository;
import learn.hashbrown_hashers.models.Recipe;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class RecipeService {

    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> findAll() {return repository.findAllRecipes();}

    public Recipe findById(int recipeId) {return repository.findById(recipeId);}

    public List<Recipe> findByUserId(int userId){
        return repository.findByUserId(userId);
    }

    public List<Recipe> findByText( String text){
        return repository.findByText(text);
    }

    public Result<Recipe> add(Recipe recipe) {
        Result<Recipe> result = new Result<>();

        if (recipe == null) {
            result.addMessage("Recipe cannot be null", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();


        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Recipe> violation : violations) {
                result.addMessage(violation.getMessage(),ResultType.INVALID);
            }
            return result;
        }

        if(recipe.getRecipeId() != 0){
            result.addMessage("recipeId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        recipe = repository.add(recipe);
        result.setPayload(recipe);
        return result;
    }

    public Result<Recipe> update(Recipe recipe){

        Result<Recipe> result = new Result<>();


        if (recipe == null) {
            result.addMessage("Recipe cannot be null", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();


        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Recipe> violation : violations) {
                result.addMessage(violation.getMessage(),ResultType.INVALID);
            }
            return result;
        }

        if(recipe.getRecipeId() <= 0){
            result.addMessage("recipeId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if(!repository.update(recipe)){
            String msg = String.format("recipeId: %s, not found", recipe.getRecipeId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int recipeId) {
        return repository.deleteById(recipeId);
    }


}
