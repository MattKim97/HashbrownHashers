package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagRepository  {

    List<Tag> findAll();

    Tag findById(int tagId);

    List<Tag> findByText(String text);


    Tag add(Tag tag);


    @Transactional
    boolean deleteById(int tagId);




}
