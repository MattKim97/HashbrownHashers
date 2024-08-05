package learn.hashbrown_hashers.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Recipe {
    private int recipeId;
    private String recipeName;
    private int difficulty;
    private int spicyness;
    private int prepTime;
    private String imageUrl;
    private String description;
    private String text;
    private int userId;
    private LocalDate timePosted;
    private LocalDate timeUpdated;
    private List<Tag> tags = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(String recipeName, int recipeId, int difficulty, int spicyness, int prepTime, String imageUrl, String description, String text, int userId, LocalDate timePosted, LocalDate timeUpdated, List<Tag> tags) {
        this.recipeName = recipeName;
        this.recipeId = recipeId;
        this.difficulty = difficulty;
        this.spicyness = spicyness;
        this.prepTime = prepTime;
        this.imageUrl = imageUrl;
        this.description = description;
        this.text = text;
        this.userId = userId;
        this.timePosted = timePosted;
        this.timeUpdated = timeUpdated;
        this.tags = tags;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getSpicyness() {
        return spicyness;
    }

    public void setSpicyness(int spicyness) {
        this.spicyness = spicyness;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(LocalDate timePosted) {
        this.timePosted = timePosted;
    }

    public LocalDate getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(LocalDate timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return recipeId == recipe.recipeId && difficulty == recipe.difficulty && spicyness == recipe.spicyness && prepTime == recipe.prepTime && userId == recipe.userId && Objects.equals(recipeName, recipe.recipeName) && Objects.equals(imageUrl, recipe.imageUrl) && Objects.equals(description, recipe.description) && Objects.equals(text, recipe.text) && Objects.equals(timePosted, recipe.timePosted) && Objects.equals(timeUpdated, recipe.timeUpdated) && Objects.equals(tags, recipe.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, recipeName, difficulty, spicyness, prepTime, imageUrl, description, text, userId, timePosted, timeUpdated, tags);
    }
}
