package learn.hashbrown_hashers.models;

public class RecipeTag {

    private int recipeId;
    private int tagId;
    private String tagName;


    public RecipeTag(int recipeId, int tagId, String tagName) {
        this.recipeId = recipeId;
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public RecipeTag() {

    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
