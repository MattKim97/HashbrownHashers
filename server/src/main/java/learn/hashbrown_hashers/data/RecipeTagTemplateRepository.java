package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.data.mappers.RecipeTagMapper;
import learn.hashbrown_hashers.models.RecipeTag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class RecipeTagTemplateRepository implements RecipeTagRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecipeTagTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<RecipeTag> findbyRecipeId(int recipeId) {
        final String sql = "select rt.recipe_id, rt.tag_id, t.tag_name " +
                "from recipe_tags rt inner join tags t on t.tag_id=rt.tag_id " +
                "where rt.recipe_id=?;";
        return jdbcTemplate.query(sql,new RecipeTagMapper(), recipeId);
    }

    @Override
    public List<RecipeTag> findbyTagId(int tagId) {
        final String sql = "select rt.recipe_id, rt.tag_id, t.tag_name " +
                "from recipe_tags rt inner join tags t on t.tag_id=rt.tag_id " +
                "where rt.tag_id=?;";
        return jdbcTemplate.query(sql,new RecipeTagMapper(), tagId);
    }


    @Override
    @Transactional
    public RecipeTag add(RecipeTag recipeTag) {
        final String sql = "insert into recipe_tags " +
                "values(?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, recipeTag.getTagId());
            ps.setInt(2, recipeTag.getRecipeId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0){
            return null;
        }

        return recipeTag;
    }

}
