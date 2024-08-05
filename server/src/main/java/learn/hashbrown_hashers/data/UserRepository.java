package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.User;
import java.util.List;

public interface UserRepository {
    User findById(int userId);

    List<User> findAll();

    User add(User user);

    boolean update(User user);

    boolean deleteById(int userId);
}
