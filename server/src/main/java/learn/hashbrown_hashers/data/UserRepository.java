package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.User;
import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(Integer userId);

    User add(User user);

    boolean update(User user);

    boolean deleteById(Integer userId);

    User findByUsername(String username);

    User findByEmail(String email);

    // New methods to handle authorization
    boolean deleteUser(User currentUser, Integer userIdToDelete);

}
