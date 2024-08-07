package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.UserRepository;
import learn.hashbrown_hashers.models.User;
import learn.hashbrown_hashers.security.JwtConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtConverter jwtConverter;

    @InjectMocks
    private UserService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldAddValidUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe");
        user.setPasswordHash("password");
        user.setEmail("johndoe@example.com");
        user.setRoleId(1);

        when(repository.findByUsername(user.getUserName())).thenReturn(null);
        when(repository.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(repository.add(any(User.class))).thenReturn(user);

        Result<User> result = service.add(user);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("John", result.getPayload().getFirstName());
    }

    @Test
    void shouldNotAddUserWithDuplicateUsername() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe");
        user.setPasswordHash("password");
        user.setEmail("johndoe@example.com");
        user.setRoleId(1);

        when(repository.findByUsername(user.getUserName())).thenReturn(user);

        Result<User> result = service.add(user);

        assertFalse(result.isSuccess());
        assertEquals("username is already in use", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddUserWithDuplicateEmail() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe");
        user.setPasswordHash("password");
        user.setEmail("johndoe@example.com");
        user.setRoleId(1);

        when(repository.findByEmail(user.getEmail())).thenReturn(user);

        Result<User> result = service.add(user);

        assertFalse(result.isSuccess());
        assertEquals("email is already in use", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddUserWithInvalidRole() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe");
        user.setPasswordHash("password");
        user.setEmail("johndoe@example.com");
        user.setRoleId(null);

        Result<User> result = service.add(user);

        assertFalse(result.isSuccess());
        assertEquals("Role ID is required", result.getMessages().get(0));
    }

    @Test
    void shouldUpdateValidUser() {
        User user = new User();
        user.setUserId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe");
        user.setPasswordHash("password");
        user.setEmail("johndoe@example.com");
        user.setRoleId(1);

        User existingUser = new User();
        existingUser.setUserId(1);
        existingUser.setUserName("johndoe");
        existingUser.setEmail("johndoe@example.com");

        when(repository.findById(user.getUserId())).thenReturn(existingUser);
        when(repository.update(any(User.class))).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        Result<User> result = service.update(user);

        assertTrue(result.isSuccess());
        assertEquals(user, result.getPayload());
    }

    @Test
    void shouldNotUpdateUserWithDuplicateUsername() {
        User user = new User();
        user.setUserId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe");
        user.setPasswordHash("password");
        user.setEmail("johndoe@example.com");
        user.setRoleId(1);

        User existingUser = new User();
        existingUser.setUserId(1);
        existingUser.setUserName("oldusername");
        existingUser.setEmail("oldemail@example.com");

        when(repository.findById(user.getUserId())).thenReturn(existingUser);
        when(repository.findByUsername(user.getUserName())).thenReturn(new User());

        Result<User> result = service.update(user);

        assertFalse(result.isSuccess());
        assertEquals("username is already in use", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateUserWithDuplicateEmail() {
        User user = new User();
        user.setUserId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe");
        user.setPasswordHash("password");
        user.setEmail("johndoe@example.com");
        user.setRoleId(1);

        User existingUser = new User();
        existingUser.setUserId(1);
        existingUser.setUserName("oldusername");
        existingUser.setEmail("oldemail@example.com");

        when(repository.findById(user.getUserId())).thenReturn(existingUser);
        when(repository.findByEmail(user.getEmail())).thenReturn(new User());

        Result<User> result = service.update(user);

        assertFalse(result.isSuccess());
        assertEquals("email is already in use", result.getMessages().get(0));
    }

    @Test
    void shouldDeleteUserWithAdminRights() {
        User adminUser = new User();
        adminUser.setUserId(1);
        adminUser.setRoleId(2); // Admin role

        User userToDelete = new User();
        userToDelete.setUserId(2);

        when(repository.deleteUser(adminUser, userToDelete.getUserId())).thenReturn(true);

        Result<Void> result = service.deleteById(userToDelete.getUserId(), adminUser);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteUserWithoutAdminRights() {
        User normalUser = new User();
        normalUser.setUserId(1);
        normalUser.setRoleId(1); // Normal user role

        User userToDelete = new User();
        userToDelete.setUserId(2);

        when(repository.deleteUser(normalUser, userToDelete.getUserId())).thenReturn(false);

        Result<Void> result = service.deleteById(userToDelete.getUserId(), normalUser);

        assertFalse(result.isSuccess());
        assertEquals("Failed to delete user or unauthorized", result.getMessages().get(0));
    }

    @Test
    void shouldFindAllUsers() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe");
        user.setPasswordHash("hash");
        user.setEmail("john.doe@example.com");
        user.setRoleId(1);

        List<User> expected = new ArrayList<>();
        expected.add(user);
        when(repository.findAll()).thenReturn(expected);
        List<User> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindUserById() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe");
        user.setPasswordHash("hash");
        user.setEmail("john.doe@example.com");
        user.setRoleId(1);

        when(repository.findById(1)).thenReturn(user);
        User actual = service.findById(1);
        assertEquals(user, actual);
    }

    @Test
    void shouldNotAddInvalidUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe");
        user.setPasswordHash("hash");
        user.setEmail("john.doe@example.com");
        user.setRoleId(1);

        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());

        user.setUserId(1); // Set userId to simulate invalid `add` operation
        result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdateUser() {
        User user = new User();
        user.setUserId(1); // Ensure that the user has a valid ID
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe");
        user.setPasswordHash("password");
        user.setEmail("johndoe@example.com");
        user.setRoleId(1);

        // Set up an existing user with the same ID but different username and email
        User existingUser = new User();
        existingUser.setUserId(user.getUserId());
        existingUser.setUserName("existinguser"); // Different username
        existingUser.setEmail("existing.email@example.com"); // Different email

        // Configure mocks
        when(repository.findById(user.getUserId())).thenReturn(existingUser);
        when(repository.findByUsername(user.getUserName())).thenReturn(null); // No duplicate username
        when(repository.findByEmail(user.getEmail())).thenReturn(null); // No duplicate email
        when(passwordEncoder.encode(user.getPasswordHash())).thenReturn("encodedHash");
        when(repository.update(user)).thenReturn(true);

        // Call the service method
        Result<User> result = service.update(user);

        // Assertions
        assertTrue(result.isSuccess(), "Result should be successful");
        assertEquals(user, result.getPayload(), "Updated user should match the input user");
        verify(repository).update(user); // Verify that the repository's update method was called
    }


    @Test
    void shouldDeleteUserById() {
        User adminUser = new User();
        adminUser.setUserId(1); // Assuming this is an admin
        adminUser.setRoleId(2); // Admin role

        User userToDelete = new User();
        userToDelete.setUserId(2);

        when(repository.deleteUser(adminUser, userToDelete.getUserId())).thenReturn(true);

        Result<Void> result = service.deleteById(userToDelete.getUserId(), adminUser);

        assertTrue(result.isSuccess());
        assertTrue(result.getMessages().isEmpty());
    }

    @Test
    void shouldNotDeleteNonExistentUserById() {
        User adminUser = new User();
        adminUser.setUserId(1); // Assuming this is an admin
        adminUser.setRoleId(2); // Admin role

        when(repository.deleteUser(adminUser, 1)).thenReturn(false);
        Result<Void> result = service.deleteById(1, adminUser);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Failed to delete user or unauthorized"));
    }
}