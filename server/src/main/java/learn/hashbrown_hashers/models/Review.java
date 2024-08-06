package learn.hashbrown_hashers.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Review {
    private int reviewId;

    @NotBlank(message = "Reviews must have a userId.")
    private int userId;

    @NotBlank(message = "Reviews must have a recipeId.")
    private int recipeId;
    private String title;
    private String description;

    @NotBlank(message = "Reviews must have a rating.")
    @Min(value = 1, message = "Reviews must have at least 1 star.")
    @Max(value = 5, message = "Reviews can only have 5 stars maximum.")
    private int rating;

    public Review(int userId, int reviewId, int recipeId, String title, String description, int rating) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.recipeId = recipeId;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public Review() {
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return reviewId == review.reviewId && userId == review.userId && recipeId == review.recipeId && rating == review.rating && Objects.equals(title, review.title) && Objects.equals(description, review.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, userId, recipeId, title, description, rating);
    }
}
