package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.data.mappers.TagMapper;
import learn.hashbrown_hashers.models.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.nimbus.State;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TagTemplateRepository implements TagRepository{
    private final JdbcTemplate jdbcTemplate;

    public TagTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Tag> findAll() {
        final String sql = "select tag_id, tag_name "
                + "from tags limit 1000;";
        return jdbcTemplate.query(sql,new TagMapper());
    }

    @Override
    public Tag findById(int tagId) {
        final String sql = "select tag_id, tag_name "
                + "from tags "
                + "where tag_id = ?;";
        return jdbcTemplate.query(sql,new TagMapper(),tagId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Tag> findByText(String text) {
        String newText = "%" + text + "%";
        final String sql = "select tag_id, tag_name "
                + "from tags "
                + "where tag_name LIKE ?;";
        return jdbcTemplate.query(sql,new TagMapper(),newText);
    }


    @Override
    public Tag add(Tag tag) {
        final String sql = "insert into tags (tag_name) "
                + " values (?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,tag.getTagName());
            return ps;
        },keyHolder);
        if(rowsAffected <= 0){
            return null;
        }
        tag.setTagId(keyHolder.getKey().intValue());
        return tag;
    }

    @Override
    public boolean deleteById(int tagId) {
        return jdbcTemplate.update("delete from tags where tag_id = ?;", tagId) > 0;
    }
}
