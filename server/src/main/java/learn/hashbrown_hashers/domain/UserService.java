package learn.hashbrown_hashers.domain;

import learn.hashbrown_hashers.data.UserRepository;
import learn.hashbrown_hashers.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(int userId) {
        return repository.findById(userId);
    }

    public Result<User> add(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
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

        user = repository.add(user);
        if (user == null) {
            result.addMessage("Failed to add user", ResultType.INVALID);
        } else {
            result.setPayload(user);
        }
        return result;
    }

    public Result<User> update(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() <= 0) {
            result.addMessage("userId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        User existingUserByUsername = repository.findByUsername(user.getUserName());
        if (existingUserByUsername != null && existingUserByUsername.getUserId() != user.getUserId()) {
            result.addMessage("username is already in use", ResultType.INVALID);
            return result;
        }

        User existingUserByEmail = repository.findByEmail(user.getEmail());
        if (existingUserByEmail != null && existingUserByEmail.getUserId() != user.getUserId()) {
            result.addMessage("email is already in use", ResultType.INVALID);
            return result;
        }

        boolean updateSuccessful = repository.update(user);
        if (!updateSuccessful) {
            String msg = String.format("userId: %d not found", user.getUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        } else {
            result.setPayload(user);
        }

        return result;
    }

    public boolean deleteById(int userId) {
        return repository.deleteById(userId);
    }

    private Result<User> validate(User user) {
        Result<User> result = new Result<>();
        if (user == null) {
            result.addMessage("user cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(user.getFirstName())) {
            result.addMessage("firstName is required", ResultType.INVALID);
        } else if (user.getFirstName().length() < 2 || user.getFirstName().length() > 50) {
            result.addMessage("firstName must be between 2 and 50 characters", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getLastName())) {
            result.addMessage("lastName is required", ResultType.INVALID);
        } else if (user.getLastName().length() < 2 || user.getLastName().length() > 50) {
            result.addMessage("lastName must be between 2 and 50 characters", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getUserName())) {
            result.addMessage("username is required", ResultType.INVALID);
        } else if (user.getUserName().length() < 3 || user.getUserName().length() > 20) {
            result.addMessage("username must be between 3 and 20 characters", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getEmail())) {
            result.addMessage("email is required", ResultType.INVALID);
        } else if (!isValidEmail(user.getEmail())) {
            result.addMessage("email format is invalid", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getPasswordHash())) {
            result.addMessage("passwordHash is required", ResultType.INVALID);
        }

        if (user.getRoleId() <= 0) {
            result.addMessage("roleId must be greater than 0", ResultType.INVALID);
        }

        return result;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
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
