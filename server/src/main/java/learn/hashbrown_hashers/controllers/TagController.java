package learn.hashbrown_hashers.controllers;


import learn.hashbrown_hashers.domain.Result;
import learn.hashbrown_hashers.domain.TagService;
import learn.hashbrown_hashers.models.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/tags")
public class TagController {
    private final TagService service;


    public TagController(TagService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Tag> findAll(){
        return service.findAll();
    }

    @GetMapping("/{text}")
    public List<Tag> findByText(@PathVariable String text){
        return service.findByText(text);
    }


    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Tag tag){
        Result<Tag> result = service.add(tag);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }


    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteById(@PathVariable int tagId){
        if(service.deleteById(tagId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
