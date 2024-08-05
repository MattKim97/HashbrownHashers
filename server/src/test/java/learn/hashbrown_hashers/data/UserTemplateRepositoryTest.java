package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserTemplateRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserTemplateRepository userRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    @Test
    @Transactional
    void testFindAll() {
        List<User> users = userRepository.findAll();
        assertEquals(10, users.size(), "The number of users should be 10.");
    }

    @Test
    @Transactional
    void testAddUser() {
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setUserName("johndoe");
        newUser.setPasswordHash("hash");
        newUser.setEmail("john.doe@example.com");
        newUser.setRoleId(1);

        User addedUser = userRepository.add(newUser);
        assertNotNull(addedUser, "The added user should not be null.");
        assertNotNull(addedUser.getUserId(), "The added user should have a generated ID.");

        User fetchedUser = userRepository.findById(addedUser.getUserId());
        assertNotNull(fetchedUser, "The fetched user should not be null.");
        assertEquals(newUser.getUserName(), fetchedUser.getUserName(), "The user names should match.");
    }

    @Test
    @Transactional
    void testUpdateUser() {
        User existingUser = userRepository.findById(1);
        assertNotNull(existingUser, "The existing user should not be null.");

        existingUser.setFirstName("UpdatedName");
        boolean updated = userRepository.update(existingUser);
        assertTrue(updated, "The user should be updated successfully.");

        User updatedUser = userRepository.findById(1);
        assertEquals("UpdatedName", updatedUser.getFirstName(), "The user's first name should be updated.");
    }

    @Test
    @Transactional
    void testDeleteUser() {
        User userToDelete = userRepository.findById(3);
        assertNotNull(userToDelete, "The user to delete should not be null.");

        boolean deleted = userRepository.deleteById(3);
        assertTrue(deleted, "The user should be deleted successfully.");

        User deletedUser = userRepository.findById(3);
        assertNull(deletedUser, "The user should be null after deletion.");
    }

    @Test
    @Transactional
    void testFindByUsername() {
        User user = userRepository.findByUsername("vickerstaff0");
        assertNotNull(user, "The user with the given username should not be null.");
        assertEquals("Leelah", user.getFirstName(), "The user's first name should match.");
    }

    @Test
    @Transactional
    void testFindByEmail() {
        User user = userRepository.findByEmail("vickerstaff0@desdev.cn");
        assertNotNull(user, "The user with the given email should not be null.");
        assertEquals("Leelah", user.getFirstName(), "The user's first name should match.");
    }
}
