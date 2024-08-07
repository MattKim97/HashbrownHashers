package learn.hashbrown_hashers.data;

import learn.hashbrown_hashers.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTemplateRepositoryTest {

    @Autowired
    private UserTemplateRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @Transactional
    public void setUp() {
        jdbcTemplate.execute("CALL set_known_good_state();"); // Reset database to a known state
    }

    @Test
    public void testCreateUser() {
        AppUser user = new AppUser(null, "John", "Doe", "john_doe", "password_hash", "john.doe@example.com", 1);
        AppUser createdUser = userRepository.create(user);
        assertNotNull(createdUser);
        assertNotNull(createdUser.getUserId());
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("john_doe", createdUser.getUserName());
        assertEquals("john.doe@example.com", createdUser.getEmail());
    }

    @Test
    public void testFindByUsername() {
        AppUser user = userRepository.findByUsername("vickerstaff0");
        assertNotNull(user);
        assertEquals("vickerstaff0", user.getUserName());
        assertEquals("Leelah", user.getFirstName());
        assertEquals("Vickerstaff", user.getLastName());
    }

    @Test
    public void testFindById() {
        AppUser user = userRepository.findById(1);
        assertNotNull(user);
        assertEquals(1, user.getUserId());
        assertEquals("Leelah", user.getFirstName());
    }

    @Test
    public void testFindByEmail() {
        AppUser user = userRepository.findByEmail("vickerstaff0@desdev.cn");
        assertNotNull(user);
        assertEquals("vickerstaff0@desdev.cn", user.getEmail());
        assertEquals("Leelah", user.getFirstName());
    }

    @Test
    public void testUpdateUser() {
        AppUser user = userRepository.findById(1);
        assertNotNull(user);
        user.setLastName("UpdatedName");
        AppUser updatedUser = userRepository.update(user);
        assertNotNull(updatedUser);
        assertEquals("UpdatedName", updatedUser.getLastName());
    }

    @Test
    public void testDeleteUser() {
        boolean isDeleted = userRepository.deleteById(1);
        assertTrue(isDeleted);
        AppUser user = userRepository.findById(1);
        assertNull(user);
    }
}
