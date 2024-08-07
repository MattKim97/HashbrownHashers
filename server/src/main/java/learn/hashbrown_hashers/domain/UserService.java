package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.UserRepository;
import learn.hashbrown_hashers.models.User;
import learn.hashbrown_hashers.security.JwtConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConverter jwtConverter;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JwtConverter jwtConverter) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtConverter = jwtConverter;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Integer userId) {
        return repository.findById(userId);
    }

    public Result<User> add(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != null) {
            result.addMessage("userId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        if (isDuplicateUsername(user.getUserName())) {
            result.addMessage("username is already in use", ResultType.INVALID);
            return result;
        }

        if (isDuplicateEmail(user.getEmail())) {
            result.addMessage("email is already in use", ResultType.INVALID);
            return result;
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        User addedUser = repository.add(user);
        if (addedUser == null) {
            result.addMessage("Failed to add user", ResultType.INVALID);
        } else {
            result.setPayload(addedUser);
        }
        return result;
    }

    public Result<User> update(User user) {
        Result<User> result = new Result<>();

        // Validate user object
        Result<User> validationResult = validate(user);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        // Check if user ID is set
        if (user.getUserId() == null) {
            result.addMessage("userId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        // Find existing user
        User existingUser = repository.findById(user.getUserId());
        if (existingUser == null) {
            result.addMessage("User not found", ResultType.NOT_FOUND);
            return result;
        }

        // Check for duplicate username and email
        if (!user.getUserName().equals(existingUser.getUserName()) && isDuplicateUsername(user.getUserName())) {
            result.addMessage("username is already in use", ResultType.INVALID);
            return result;
        }

        if (!user.getEmail().equals(existingUser.getEmail()) && isDuplicateEmail(user.getEmail())) {
            result.addMessage("email is already in use", ResultType.INVALID);
            return result;
        }

        // Update user
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        boolean success = repository.update(user);
        if (!success) {
            result.addMessage("Failed to update user", ResultType.INVALID);
        } else {
            result.setPayload(user);
        }
        return result;
    }

    @Transactional
    public Result<Void> deleteById(Integer userId, User currentUser) {
        Result<Void> result = new Result<>();

        if (currentUser == null) {
            result.addMessage("Current user cannot be null", ResultType.INVALID);
            return result;
        }

        if (!repository.deleteUser(currentUser, userId)) {
            result.addMessage("Failed to delete user or unauthorized", ResultType.INVALID);
            return result;
        }

        return result;
    }

    public Result<User> updateUser(User currentUser, User userToUpdate) {
        Result<User> result = new Result<>();
        if (currentUser == null) {
            result.addMessage("Current user cannot be null", ResultType.NOT_FOUND);
            return result;
        }


        result.setPayload(userToUpdate);
        return result;
    }

    private Result<User> validate(User user) {
        Result<User> result = new Result<>();

        if (user == null) {
            result.addMessage("User cannot be null", ResultType.INVALID);
            return result;
        }

        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            result.addMessage("First name is required", ResultType.INVALID);
        }

        if (user.getLastName() == null || user.getLastName().isBlank()) {
            result.addMessage("Last name is required", ResultType.INVALID);
        }

        if (user.getUserName() == null || user.getUserName().isBlank()) {
            result.addMessage("Username is required", ResultType.INVALID);
        }

        if (user.getPasswordHash() == null || user.getPasswordHash().isBlank()) {
            result.addMessage("Password hash is required", ResultType.INVALID);
        }

        if (user.getEmail() == null || user.getEmail().isBlank() || !isValidEmail(user.getEmail())) {
            result.addMessage("Invalid email address", ResultType.INVALID);
        }

        if (user.getRoleId() == null) {
            result.addMessage("Role ID is required", ResultType.INVALID);
        }

        return result;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isDuplicateUsername(String username) {
        return repository.findByUsername(username) != null;
    }

    private boolean isDuplicateEmail(String email) {
        return repository.findByEmail(email) != null;
    }
}
