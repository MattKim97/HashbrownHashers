package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TagRepositoryTest {

    @Autowired
    TagTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {knownGoodState.set();}


    @Test
    void shouldFindAll() {
        List<Tag> tags = repository.findAll();
        assertNotNull(tags);
        assertTrue(tags.size() >= 5);
    }

    @Test
    void shouldFindById() {
        Tag tag = repository.findById(1);
        assertNotNull(tag);
        assertEquals("Hot",tag.getTagName());
    }

    @Test
    void shouldFindByText() {
        List<Tag> tags = repository.findByText("uice");
        assertNotNull(tags);
        assertEquals(tags.size(),1);
        assertEquals(tags.get(0).getTagName(),"Juice");
    }

    @Test
    void shouldAdd() {
        Tag tag = new Tag(0,"Salty");
        Tag result = repository.add(tag);
        assertNotNull(result);
        assertEquals(result,tag);
        assertEquals(result.getTagId(),6);
    }

    @Test
    void deleteById() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));
    }
}