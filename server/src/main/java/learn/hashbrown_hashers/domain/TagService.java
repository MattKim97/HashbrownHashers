package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.TagRepository;
import learn.hashbrown_hashers.models.Review;
import learn.hashbrown_hashers.models.Tag;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class TagService {

    private final TagRepository repository;


    public TagService(TagRepository repository) {
        this.repository = repository;
    }


    //find list of all tags
    public List<Tag> findAll(){
        return repository.findAll();
    }

    //find list of tags that contain input string. to be used for tag search.
    public List<Tag> findByText(String text){
        return repository.findByText(text);
    }

    public Result<Tag> add(Tag tag){
        Result<Tag> result = checker(tag);
        isUnique(result, tag);
        if(!result.isSuccess()){
            return result;
        }
        if(tag.getTagId() != 0){
            result.addMessage("Tag cannot have ID present upon creation", ResultType.INVALID);
            return result;
        }
        tag = repository.add(tag);
        result.setPayload(tag);
        return result;
    }

    public boolean deleteById(int id){
        return repository.deleteById(id);
    }




    private Result<Tag> checker(Tag tag) {
        Result<Tag> result = new Result<>();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()){
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Tag>> validation = validator.validate(tag);

            validation.forEach(v -> result.addMessage(v.getMessage(), ResultType.INVALID));
        }
        return result;
    }

    private void isUnique(Result<Tag> result, Tag tag){
        List<Tag> allTags = repository.findAll();
        for(Tag t:allTags){
            if(Objects.equals(t.getTagName(), tag.getTagName())){
                result.addMessage("Tag name must be unique",ResultType.INVALID);
            }
        }

    }


}
