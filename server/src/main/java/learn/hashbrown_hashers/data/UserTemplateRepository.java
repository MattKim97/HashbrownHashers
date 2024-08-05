package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findById(int userId) {
        final String sql = "SELECT user_id, first_name, last_name, username, password_hash, email, role_id " +
                "FROM recipe_users WHERE user_id = ?;";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), userId);
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT user_id, first_name, last_name, username, password_hash, email, role_id FROM recipe_users;";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User add(User user) {
        final String sql = "INSERT INTO recipe_users (first_name, last_name, username, password_hash, email, role_id) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getPasswordHash(),
                user.getEmail(),
                user.getRoleId());
        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        user.setUserId(newId);
        return user;
    }

    @Override
    public boolean update(User user) {
        final String sql = "UPDATE recipe_users SET " +
                "first_name = ?, " +
                "last_name = ?, " +
                "username = ?, " +
                "password_hash = ?, " +
                "email = ?, " +
                "role_id = ? " +
                "WHERE user_id = ?;";
        return jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getPasswordHash(),
                user.getEmail(),
                user.getRoleId(),
                user.getUserId()) > 0;
    }

    @Override
    public boolean deleteById(int userId) {
        final String sql = "DELETE FROM recipe_users WHERE user_id = ?;";
        return jdbcTemplate.update(sql, userId) > 0;
    }
}
