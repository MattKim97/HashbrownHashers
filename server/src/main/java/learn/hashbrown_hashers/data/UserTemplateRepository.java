package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.data.mappers.UserMapper;
import learn.hashbrown_hashers.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public UserTemplateRepository(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT user_id, first_name, last_name, username, password_hash, email, role_id FROM recipe_users;";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    @Transactional
    public User findById(Integer userId) {
        final String sql = "SELECT user_id, first_name, last_name, username, password_hash, email, role_id FROM recipe_users WHERE user_id = ?;";
        return jdbcTemplate.query(sql, new UserMapper(), userId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public User add(User user) {
        final String sql = "INSERT INTO recipe_users (first_name, last_name, username, password_hash, email, role_id) VALUES (?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            ps.setString(4, passwordEncoder.encode(user.getPasswordHash())); // Encode password
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getRoleId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public boolean update(User user) {
        final String sql = "UPDATE recipe_users SET first_name = ?, last_name = ?, username = ?, password_hash = ?, email = ?, role_id = ? WHERE user_id = ?;";

        return jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                passwordEncoder.encode(user.getPasswordHash()), // Encode password
                user.getEmail(),
                user.getRoleId(),
                user.getUserId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(Integer userId) {
        return jdbcTemplate.update("DELETE FROM recipe_users WHERE user_id = ?;", userId) > 0;
    }

    @Override
    public User findByUsername(String username) {
        final String sql = "SELECT user_id, first_name, last_name, username, password_hash, email, role_id FROM recipe_users WHERE username = ?;";
        return jdbcTemplate.query(sql, new UserMapper(), username).stream()
                .findFirst().orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        final String sql = "SELECT user_id, first_name, last_name, username, password_hash, email, role_id FROM recipe_users WHERE email = ?;";
        return jdbcTemplate.query(sql, new UserMapper(), email).stream()
                .findFirst().orElse(null);
    }

    // New methods to handle authorization
    @Transactional
    public boolean deleteUser(User currentUser, Integer userIdToDelete) {
        if (currentUser.isAdmin() || currentUser.getUserId().equals(userIdToDelete)) {
            return deleteById(userIdToDelete);
        }
        return false; // Unauthorized
    }

}
