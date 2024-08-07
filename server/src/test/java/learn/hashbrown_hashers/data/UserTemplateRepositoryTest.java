package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.User;
import learn.hashbrown_hashers.data.mappers.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTemplateRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserTemplateRepository repository;

    @Autowired
    private KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }



    @Test

    void testFindAll() {
        List<User> users = repository.findAll();
        System.out.println("Number of users in database: " + users.size());
        users.forEach(user -> System.out.println("User: " + user.getUserName()));
        assertThat(users).hasSize(11);
    }
    @Test
    void testFindById() {
        User user = repository.findById(1);
        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo("vickerstaff0");
    }

    @Test
    void testAdd() {
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setUserName("john_doe");
        newUser.setPasswordHash("password");
        newUser.setEmail("john.doe@example.com");
        newUser.setRoleId(1);

        User addedUser = repository.add(newUser);

        assertThat(addedUser).isNotNull();
        assertThat(addedUser.getUserId()).isGreaterThan(0);

        User foundUser = repository.findById(addedUser.getUserId());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo("john_doe");
    }

    @Test
    void testUpdate() {
        User user = repository.findById(1);
        assertThat(user).isNotNull();

        user.setEmail("new.email@example.com");
        boolean updated = repository.update(user);

        assertThat(updated).isTrue();

        User updatedUser = repository.findById(1);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getEmail()).isEqualTo("new.email@example.com");
    }

    @Test
    @Transactional
    void testDeleteById() {
        boolean deleted = repository.deleteById(10);
        assertThat(deleted).isTrue();

        User user = repository.findById(10);
        assertThat(user).isNull();
    }

    @Test
    void testFindByUsername() {
        User user = repository.findByUsername("admin");
        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo("admin");
    }

    @Test
    void testFindByEmail() {
        User user = repository.findByEmail("admin@cam.ac.uk");
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("admin@cam.ac.uk");
    }

    @Test
    void testDeleteUser() {
        User adminUser = repository.findByUsername("admin");
        assertThat(adminUser).isNotNull();

        User userToDelete = repository.findById(2); // Example ID
        boolean deleted = repository.deleteUser(adminUser, userToDelete.getUserId());

        assertThat(deleted).isTrue();
        User deletedUser = repository.findById(userToDelete.getUserId());
        assertThat(deletedUser).isNull();
    }
}
