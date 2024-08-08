package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public AppUser findByUsername(String username) {
        final String sql = "SELECT user_id, first_name, last_name, username, password_hash, email, role_id "
                + "FROM recipe_users "
                + "WHERE username = ?;";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new AppUser(
                                rs.getInt("user_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("username"),
                                rs.getString("password_hash"),
                                rs.getString("email"),
                                rs.getInt("role_id")
                        ), username)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public AppUser findById(Integer userId) {
        final String sql = "SELECT user_id, first_name, last_name, username, password_hash, email, role_id "
                + "FROM recipe_users "
                + "WHERE user_id = ?;";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new AppUser(
                                rs.getInt("user_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("username"),
                                rs.getString("password_hash"),
                                rs.getString("email"),
                                rs.getInt("role_id")
                        ), userId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public AppUser findByEmail(String email) {
        final String sql = "SELECT user_id, first_name, last_name, username, password_hash, email, role_id "
                + "FROM recipe_users "
                + "WHERE email = ?;";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new AppUser(
                                rs.getInt("user_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("username"),
                                rs.getString("password_hash"),
                                rs.getString("email"),
                                rs.getInt("role_id")
                        ), email)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public AppUser create(AppUser user) {
        final String sql = "INSERT INTO recipe_users (first_name, last_name, username, password_hash, email, role_id) "
                + "VALUES (?, ?, ?, ?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getPasswordHash());
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
    @Transactional
    public AppUser update(AppUser user) {
        final String sql = "UPDATE recipe_users SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "username = ?, "
                + "password_hash = ?, "
                + "email = ?, "
                + "role_id = ? "
                + "WHERE user_id = ?;";

        jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getPasswordHash(),
                user.getEmail(),
                user.getRoleId(),
                user.getUserId());
        return user;
    }

    @Override
    @Transactional
    public boolean deleteById(Integer userId) {
        final String sql = "DELETE FROM recipe_users WHERE user_id = ?;";
        return jdbcTemplate.update(sql, userId) > 0;
    }

    // Additional methods for security management

    @Transactional
    public Collection<GrantedAuthority> getAuthoritiesByUsername(String username) {
        final String sql = "SELECT r.role_title "
                + "FROM recipe_users ru "
                + "JOIN user_roles r ON ru.role_id = r.role_id "
                + "WHERE ru.username = ?";

        List<String> roles = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("role_title"), username);

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }
}
