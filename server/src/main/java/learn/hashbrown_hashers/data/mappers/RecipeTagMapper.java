package learn.hashbrown_hashers.data.mappers;

import learn.hashbrown_hashers.models.RecipeTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeTagMapper  implements RowMapper<RecipeTag> {
    @Override
    public RecipeTag mapRow(ResultSet resultSet, int i) throws SQLException {
        RecipeTag tag = new RecipeTag(
                resultSet.getInt("recipe_id"),
                resultSet.getInt("tag_id"),
                resultSet.getString("tag_name")
        );

        return tag;
    }
}
