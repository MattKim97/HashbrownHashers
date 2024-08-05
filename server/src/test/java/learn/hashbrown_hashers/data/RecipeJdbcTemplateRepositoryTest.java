package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.Recipe;
import learn.hashbrown_hashers.models.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RecipeJdbcTemplateRepositoryTest {

    @Autowired
    RecipeJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {knownGoodState.set();}

    @Test
    void shouldFindAll(){
        List<Recipe> recipes = repository.findAllRecipes();
        assertNotNull(recipes);

        assertTrue(recipes.size() >= 2 && recipes.size() <= 5);

    }

    @Test
    void shouldFindSpaghetti(){
        Recipe spaghetti = repository.findById(1);
        assertEquals(1,spaghetti.getRecipeId());
        assertEquals(2,spaghetti.getDifficulty());
        assertEquals(1,spaghetti.getSpicyness());
        assertEquals(20,spaghetti.getPrepTime());
    }

    @Test
    void shouldFindByUserId(){
        List<Recipe> myRecipes = repository.findByUserId(2);
        assertEquals(myRecipes.size(),1);
        Recipe spaghetti = myRecipes.get(0);
        assertEquals(1,spaghetti.getRecipeId());
        assertEquals(2,spaghetti.getDifficulty());
        assertEquals(1,spaghetti.getSpicyness());
        assertEquals(20,spaghetti.getPrepTime());
    }

    @Test
    void shouldFindByText(){
        List<Recipe> hotRecipes = repository.findByText("Spag");
        assertEquals(hotRecipes.size(),1);
        Recipe spaghetti = hotRecipes.get(0);
        assertEquals(1,spaghetti.getRecipeId());
        assertEquals(2,spaghetti.getDifficulty());
        assertEquals(1,spaghetti.getSpicyness());
        assertEquals(20,spaghetti.getPrepTime());
    }

    @Test
    void shouldAdd(){
        Recipe recipe = new Recipe();
        recipe.setRecipeName("Test");
        recipe.setDifficulty(3);
        recipe.setSpicyness(3);
        recipe.setPrepTime(30);
        recipe.setDescription("Test Test Test");
        recipe.setText("Something about a recipe");
        recipe.setUserId(1);

        Recipe actual = repository.add(recipe);
        assertNotNull(actual);
        assertEquals(4,recipe.getRecipeId());
    }

    @Test
    void shouldUpdate(){
        Recipe recipe = new Recipe();
        recipe.setRecipeName("Test");
        recipe.setDifficulty(3);
        recipe.setSpicyness(3);
        recipe.setPrepTime(30);
        recipe.setDescription("Test Test Test");
        recipe.setText("Something about a recipe");
        recipe.setUserId(3);
        recipe.setRecipeId(2);
        assertTrue(repository.update(recipe));
        recipe.setRecipeId(99);
        assertFalse(repository.update(recipe));
    }

    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(99));
    }
}