package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.TagRepository;
import learn.hashbrown_hashers.models.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TagServiceTest {

    @Autowired
    TagService service;

    @MockBean
    TagRepository repository;




    @Test
    void shouldFindByText() {
        Tag tag = makeTag();
        ArrayList<Tag> expected = new ArrayList<>();
        expected.add(tag);
        when(repository.findByText("spicy")).thenReturn(expected);
        List<Tag> actual = service.findByText("spicy");
        assertEquals(expected,actual);
    }

    @Test
    void shouldNotAddInvalidTag(){
        Tag tag = makeTag();
        Result<Tag> result = service.add(tag);
        assertEquals(ResultType.INVALID,result.getType());

        tag.setTagId(0);
        result = service.add(tag);
        assertTrue(result.isSuccess());

    }

    @Test
    void shouldNotMakeDuplicateTag(){
        Tag tag = makeTag();
        ArrayList<Tag> expected = new ArrayList<>();
        expected.add(tag);
        when(repository.findAll()).thenReturn(expected);
        Tag tag2 = makeTag();
        tag2.setTagId(0);
        Result<Tag> result = service.add(tag);
        assertEquals(ResultType.INVALID,result.getType());
        System.out.println(result.getMessages());
    }


    Tag makeTag(){
        return new Tag(1,"Spicy");
    }

}