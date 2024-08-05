package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;





@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReviewRepositoryTest {

    @Autowired
    ReviewTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {knownGoodState.set();}


    @Test
    void shouldFindAll(){
        List<Review> reviews = repository.findAll();
        assertNotNull(reviews);
        assertEquals(reviews.size(),4);
    }

    @Test
    void shouldFindByRecipeId(){
        List<Review> reviews = repository.findByRecipeId(2);
        assertNotNull(reviews);
        assertTrue(reviews.size() >= 2);
    }

    @Test
    void shouldFindByUserId(){
        List<Review> reviews = repository.findByUserId(3);
        assertNotNull(reviews);
        assertEquals(reviews.size(),1);
        assertEquals(reviews.get(0).getRating(),4);
    }

    @Test
    void shouldAdd(){
        Review review = new Review();
        review.setUserId(1);
        review.setRecipeId(1);
        review.setTitle("This pasta is to die for!");
        review.setDescription("Seriously to die for. My wife died eating this pasta. Never making again.");
        review.setRating(1);
        Review result = repository.add(review);
        assertNotNull(result);
        assertEquals(result.getReviewId(),5);
    }

    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));
    }


  
}