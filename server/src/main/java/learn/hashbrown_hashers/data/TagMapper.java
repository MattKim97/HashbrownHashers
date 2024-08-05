package learn.hashbrown_hashers.data;


import learn.hashbrown_hashers.models.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {


    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Tag tag = new Tag(
                resultSet.getInt("tag_id"),
                resultSet.getString("tag_name")
        );
        return tag;
    }
}
