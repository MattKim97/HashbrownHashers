package learn.hashbrown_hashers.data.mappers;

import learn.hashbrown_hashers.models.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<AppUser> {

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppUser appUser = new AppUser(
                rs.getInt("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("username"),
                rs.getString("password_hash"),
                rs.getString("email"),
                rs.getInt("role_id")
        );
        return appUser;
    }
}
