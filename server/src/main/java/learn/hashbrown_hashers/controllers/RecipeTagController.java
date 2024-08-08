package learn.hashbrown_hashers.controllers;


import learn.hashbrown_hashers.domain.RecipeTagService;
import learn.hashbrown_hashers.domain.Result;
import learn.hashbrown_hashers.models.Recipe;
import learn.hashbrown_hashers.models.RecipeTag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/recipe_tags")
public class RecipeTagController {

    private final RecipeTagService service;


    public RecipeTagController(RecipeTagService service) {
        this.service = service;
    }

    @GetMapping("/{recipeId}")
    public List<RecipeTag> findByRecipeId(@PathVariable int recipeId){
        return service.findbyRecipeId(recipeId);
    }

    @PostMapping("(/{recipeId}")
    public Result<RecipeTag> addTag(@PathVariable int recipeId, @RequestBody int tagId, String tagName){
        RecipeTag recipeTag = new RecipeTag(recipeId,tagId,tagName);
        return service.add(recipeTag);
    }
}
