package learn.hashbrown_hashers.controllers;


import learn.hashbrown_hashers.domain.Result;
import learn.hashbrown_hashers.domain.ReviewService;
import learn.hashbrown_hashers.models.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }


    @GetMapping
    public List<Review> findAll(){
        return service.findAll();
    }

    @GetMapping("/{recipeId}")
    public List<Review> findByRecipeId(@PathVariable int recipeId){
        return service.findByRecipeId(recipeId);
    }

    @GetMapping("/user/{userId}")
    public List<Review> findByUserId(@PathVariable int userId){
        return service.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Review review){
        Result<Review> result = service.add(review);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteById(@PathVariable int reviewId){
        if(service.deleteById(reviewId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
