package learn.hashbrown_hashers.data;


import learn.hashbrown_hashers.models.RecipeTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RecipeTagTemplateRepositoryTest {
    @Autowired
    RecipeTagRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void findbyRecipeId() {
        List<RecipeTag> actual = repository.findbyRecipeId(2);
        assertEquals(3, actual.size());
        assertEquals(2, actual.get(1).getRecipeId());
    }

    @Test
    void findbyTagId() {
        List<RecipeTag> actual = repository.findbyTagId(4);
        assertEquals(1, actual.size());
        assertEquals(4, actual.get(0).getTagId());
    }

    @Test
    void add() {
        RecipeTag expected = new RecipeTag(1, 3, "");
        RecipeTag actual = repository.add(expected);
        assertEquals(expected, actual);
    }
}