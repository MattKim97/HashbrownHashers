package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.ReviewRepository;
import learn.hashbrown_hashers.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReviewServiceTest {
    @Autowired
    ReviewService service;

    @MockBean
    ReviewRepository repository;




    @Test
    void ShouldNotFindByRecipeIdLessThan0() {
        assertNull(service.findByRecipeId(-4));
    }

    @Test
    void findByRecipeId() {
        Review r = makeReview();
        List<Review> expected = new ArrayList<>();
        expected.add(r);
        when(repository.findByRecipeId(1)).thenReturn(expected);
        List<Review> actual = service.findByRecipeId(1);
        assertEquals(1, actual.size());
        assertEquals(expected.get(0), actual.get(0));
    }

    @Test
    void ShouldNotFindByUserIdLessThan0() {

        assertNull(service.findByUserId(-4));
    }

    @Test
    void findByUserId() {
        Review r = makeReview();
        List<Review> expected = new ArrayList<>();
        expected.add(r);
        when(repository.findByUserId(2)).thenReturn(expected);
        List<Review> actual = service.findByUserId(2);
        assertEquals(1, actual.size());
        assertEquals(expected.get(0), actual.get(0));
    }


    @Test
    void ShouldNotAddNull() {
        Result<Review> result = service.add(null);
        assertFalse(result.isSuccess());
        assertEquals("review cannot be empty.", result.getMessages().get(0));
    }

    @Test
    void ShouldNotAddRatingLessThan1() {
        Review r = makeReview();
        r.setRating(-2);
        Result<Review> result = service.add(r);
        assertFalse(result.isSuccess());
        assertEquals("Reviews must have at least 1 star.", result.getMessages().get(0));
    }

    @Test
    void ShouldNotAddRatingGreaterThan5() {
        Review r = makeReview();
        r.setRating(90);
        Result<Review> result = service.add(r);
        assertFalse(result.isSuccess());
        assertEquals("Reviews can only a maximum of 5 stars.", result.getMessages().get(0));
    }

    @Test
    void ShouldNotAddUserLessThan1() {
        Review r = makeReview();
        r.setUserId(-99);
        Result<Review> result = service.add(r);
        assertFalse(result.isSuccess());
        assertEquals("userId must be 1 or greater.", result.getMessages().get(0));
    }


    @Test
    void ShouldNotAddRecipeLessThan1() {
        Review r = makeReview();
        r.setRecipeId(-23);
        Result<Review> result = service.add(r);
        assertFalse(result.isSuccess());
        assertEquals("recipeId must be 1 or greater.", result.getMessages().get(0));
    }
    @Test
    void ShouldAdd() {
        Review r = makeReview();
        when(repository.add(r)).thenReturn(r);
        Result<Review> result = service.add(r);
        assertTrue(result.isSuccess());
        assertEquals(r, result.getPayload());
    }

    @Test
    void ShouldAddNoTitle() {
        Review r = new Review();
        r.setReviewId(1);
        r.setRating(3);
        r.setUserId(2);
        r.setRecipeId(1);
        r.setDescription("Testing");
        when(repository.add(r)).thenReturn(r);
        Result<Review> result = service.add(r);
        assertTrue(result.isSuccess());
        assertEquals(r, result.getPayload());
    }

    @Test
    void ShouldAddNoDescription() {
        Review r = new Review();
        r.setReviewId(1);
        r.setRating(3);
        r.setUserId(2);
        r.setRecipeId(1);
        r.setTitle("Testing");
        when(repository.add(r)).thenReturn(r);
        Result<Review> result = service.add(r);
        assertTrue(result.isSuccess());
        assertEquals(r, result.getPayload());
    }

    @Test
    void ShouldAddNoDescriptionNoTitle() {
        Review r = new Review();
        r.setReviewId(1);
        r.setRating(3);
        r.setUserId(2);
        r.setRecipeId(1);
        when(repository.add(r)).thenReturn(r);
        Result<Review> result = service.add(r);
        assertTrue(result.isSuccess());
        assertEquals(r, result.getPayload());
    }

    @Test
    void deleteById() {
        when(repository.deleteById(1)).thenReturn(true);
        assertTrue(service.deleteById(1));

    }
    @Test
    void ShouldNotDeleteByIdLessThan0() {
        assertFalse(service.deleteById(-2));
        assertFalse(service.deleteById(0));


    }

    private Review makeReview() {
        Review r = new Review();
        r.setReviewId(1);
        r.setRating(3);
        r.setUserId(2);
        r.setRecipeId(1);
        r.setTitle("Testing");
        r.setDescription("Testing RECIPE TITLE AND SUCH.");
        return r;
    }
}