package learn.hashbrown_hashers.controllers;


import learn.hashbrown_hashers.domain.RecipeService;
import learn.hashbrown_hashers.domain.Result;
import learn.hashbrown_hashers.models.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Recipe> findAll() {
        return service.findAll();
    }

    @GetMapping("/{recipeId}")
    public Recipe findByRecipeId(@PathVariable int recipeId){
        return service.findById(recipeId);
    }

    @GetMapping("/user/{userId}")
    public List<Recipe> findByUser(@PathVariable int userId){
        return service.findByUserId(userId);
    }

    @GetMapping("/search/{text}")
    public List<Recipe> findByText(@PathVariable String text){
        return service.findByText(text);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Recipe recipe) {
        Result<Recipe> result = service.add(recipe);

        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<Object> update(@PathVariable int recipeId, @RequestBody Recipe recipe){
        if(recipeId != recipe.getRecipeId()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Recipe> result = service.update(recipe);

        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteById(@PathVariable int recipeId) {
        if(service.deleteById(recipeId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }
