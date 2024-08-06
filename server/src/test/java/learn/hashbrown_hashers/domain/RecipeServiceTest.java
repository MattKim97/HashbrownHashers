package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.RecipeRepository;
import learn.hashbrown_hashers.models.Recipe;
import learn.hashbrown_hashers.models.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RecipeServiceTest {


    @Autowired
    RecipeService service;

    @MockBean
    RecipeRepository repository;

    @Test
    void shouldFindById(){
        Recipe expected = makeRecipe();
        when(repository.findById(1)).thenReturn(expected);
        Recipe actual = service.findById(1);
        assertEquals(expected,actual);
    }

    @Test
    void shouldAddWhenValid() {
        Recipe expected = makeRecipe();
        Recipe arg = makeRecipe();

        arg.setRecipeId(0);
        when(repository.add(arg)).thenReturn(expected);
        Result<Recipe> result = service.add(arg);
        assertEquals(ResultType.SUCCESS,result.getType());
        assertEquals(expected,result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalid(){

        // Should not add 'null'
        Result<Recipe> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if recipeId > 0
        Recipe recipe = makeRecipe();
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if recipe name is null
        recipe = makeRecipe();
        recipe.setRecipeId(0);
        recipe.setRecipeName(null);
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if recipe name is blank
        recipe = makeRecipe();
        recipe.setRecipeId(0);
        recipe.setRecipeName("");
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if recipe difficulty is not between 1 and 5
        recipe = makeRecipe();
        recipe.setRecipeId(0);
        recipe.setDifficulty(0);
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        recipe = makeRecipe();
        recipe.setRecipeId(0);
        recipe.setDifficulty(6);
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if recipe spiciness is not between 1 and 5
        recipe = makeRecipe();
        recipe.setRecipeId(0);
        recipe.setSpicyness(0);
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        recipe = makeRecipe();
        recipe.setRecipeId(0);
        recipe.setSpicyness(6);
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if recipe prep-time is less than 1
        recipe = makeRecipe();
        recipe.setRecipeId(0);
        recipe.setPrepTime(0);
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());


        // Should not add if recipe description is blank
        recipe = makeRecipe();
        recipe.setRecipeId(0);
        recipe.setDescription("");
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if recipe description is null
        recipe = makeRecipe();
        recipe.setRecipeId(0);
        recipe.setDescription(null);
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if recipe text is blank
        recipe = makeRecipe();
        recipe.setRecipeId(0);
        recipe.setText("");
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if recipe text is null
        recipe = makeRecipe();
        recipe.setRecipeId(0);
        recipe.setText(null);
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdateWhenValid() {
        Recipe arg = makeRecipe();

        when(repository.update(arg)).thenReturn(true);
        Result<Recipe> result = service.update(arg);
        assertEquals(ResultType.SUCCESS,result.getType());
    }

    @Test
    void shouldNotUpdateWhenInvalid(){

        // Should not update 'null'
        Result<Recipe> result = service.update(null);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if recipeId is <= 0
        Recipe recipe = makeRecipe();
        recipe.setRecipeId(0);
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if recipe name is null
        recipe = makeRecipe();
        recipe.setRecipeName(null);
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if recipe name is blank
        recipe = makeRecipe();
        recipe.setRecipeName("");
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if recipe difficulty is not between 1 and 5
        recipe = makeRecipe();
        recipe.setDifficulty(0);
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        recipe = makeRecipe();
        recipe.setDifficulty(6);
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if recipe spiciness is not between 1 and 5
        recipe = makeRecipe();
        recipe.setSpicyness(0);
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        recipe = makeRecipe();
        recipe.setSpicyness(6);
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if recipe prep-time is less than 1
        recipe = makeRecipe();
        recipe.setPrepTime(0);
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());


        // Should not update if recipe description is blank
        recipe = makeRecipe();
        recipe.setDescription("");
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if recipe description is null
        recipe = makeRecipe();
        recipe.setDescription(null);
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if recipe text is blank
        recipe = makeRecipe();
        recipe.setText("");
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if recipe text is null
        recipe = makeRecipe();
        recipe.setText(null);
        result = service.update(recipe);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldDelete(){
        when(repository.deleteById(1)).thenReturn(true);
        boolean result = service.deleteById(1);
        assertTrue(result);
    }

    @Test
    void shouldNotDeleteWhenNotFound(){
        when(repository.deleteById(3)).thenReturn(false);
        boolean result = service.deleteById(3);
        assertFalse(result);
    }




    private Recipe makeRecipe() {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(1);
        recipe.setRecipeName("Spicy Chicken Curry");
        recipe.setDifficulty(3);
        recipe.setSpicyness(4);
        recipe.setPrepTime(45);
        recipe.setImageUrl("http://example.com/image.jpg");
        recipe.setDescription("A delicious and spicy chicken curry perfect for any meal.");
        recipe.setText("1. Heat oil in a large pot over medium heat. Add onions and cook until golden brown. " +
                "2. Add garlic, ginger, and spices, and cook for another 2 minutes. " +
                "3. Add chicken pieces and cook until browned. " +
                "4. Add tomatoes, chicken broth, and coconut milk. Bring to a simmer and cook for 30 minutes. " +
                "5. Serve hot with rice or naan.");
        recipe.setUserId(1);
        recipe.setTimePosted(LocalDate.now());
        recipe.setTimeUpdated(LocalDate.now());

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "Spicy"));
        tags.add(new Tag(2, "Chicken"));
        recipe.setTags(tags);

        return recipe;
    }



}