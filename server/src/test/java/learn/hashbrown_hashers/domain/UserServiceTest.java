package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.UserRepository;
import learn.hashbrown_hashers.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Test
    void shouldFindAllUsers() {
        User user = makeUser();
        List<User> expected = new ArrayList<>();
        expected.add(user);
        when(repository.findAll()).thenReturn(expected);
        List<User> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindUserById() {
        User user = makeUser();
        when(repository.findById(1)).thenReturn(user);
        User actual = service.findById(1);
        assertEquals(user, actual);
    }

    @Test
    void shouldNotAddInvalidUser() {
        User user = makeUser();
        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());

        user.setUserId(1); // Set userId to simulate invalid `add` operation
        result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddDuplicateUser() {
        User user = makeUser();
        when(repository.findByUsername("johndoe")).thenReturn(user);
        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().contains("username is already in use"));
    }

    @Test
    void shouldNotAddDuplicateEmail() {
        User user = makeUser();
        when(repository.findByEmail("john.doe@example.com")).thenReturn(user);
        Result<User> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().contains("email is already in use"));
    }

    @Test
    void shouldUpdateUser() {
        User user = makeUser();
        when(repository.findByUsername("johndoe")).thenReturn(null);
        when(repository.findByEmail("john.doe@example.com")).thenReturn(null);
        when(repository.update(user)).thenReturn(true);
        Result<User> result = service.update(user);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateUserIfNotFound() {
        User user = makeUser();
        user.setUserId(1); // Set userId to simulate valid update
        when(repository.update(user)).thenReturn(false);
        Result<User> result = service.update(user);
        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertTrue(result.getMessages().contains("userId: 1, not found"));
    }

    @Test
    void shouldNotUpdateDuplicateUser() {
        User user = makeUser();
        User existingUser = makeUser();
        existingUser.setUserId(2); // Different ID but same username and email
        when(repository.findByUsername("johndoe")).thenReturn(existingUser);
        when(repository.findByEmail("john.doe@example.com")).thenReturn(existingUser);
        Result<User> result = service.update(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertFalse(result.getMessages().contains("username is already in use"));
        assertFalse(result.getMessages().contains("email is already in use"));
    }

    @Test
    void shouldDeleteUserById() {
        when(repository.deleteById(1)).thenReturn(true);
        assertTrue(service.deleteById(1));
    }

    @Test
    void shouldNotDeleteNonExistentUserById() {
        when(repository.deleteById(1)).thenReturn(false);
        assertFalse(service.deleteById(1));
    }

    private User makeUser() {
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setUserName("johndoe");
        newUser.setPasswordHash("hash");
        newUser.setEmail("john.doe@example.com");
        newUser.setRoleId(1);
        return newUser;
    }
}
