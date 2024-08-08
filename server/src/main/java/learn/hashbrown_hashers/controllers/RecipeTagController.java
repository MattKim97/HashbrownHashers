package learn.hashbrown_hashers.controllers;


import learn.hashbrown_hashers.domain.RecipeTagService;
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
}
