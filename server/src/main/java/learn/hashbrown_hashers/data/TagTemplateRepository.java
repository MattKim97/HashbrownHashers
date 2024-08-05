package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.Tag;

import java.util.List;

public class TagTemplateRepository implements TagRepository{
    @Override
    public List<Tag> findAll() {
        return List.of();
    }

    @Override
    public Tag findById(int tagId) {
        return null;
    }

    @Override
    public List<Tag> findByText(String text) {
        return List.of();
    }

    @Override
    public List<Tag> findByRecipeId(int recipeId) {
        return List.of();
    }

    @Override
    public Tag add(Tag tag) {
        return null;
    }

    @Override
    public boolean deleteById(int tagId) {
        return false;
    }
}
