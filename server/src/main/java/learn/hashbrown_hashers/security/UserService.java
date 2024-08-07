package learn.hashbrown_hashers.security;

import learn.hashbrown_hashers.data.UserRepository;
import learn.hashbrown_hashers.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user by username: " + username);

        AppUser appUser = repository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException("User with username " + username + " not found or not enabled.");
        }
        System.out.println("User found: " + appUser.getUsername());

        return appUser;
    }

    public AppUser create(String firstName, String lastName, String username, String password, String email) {
        validate(firstName, lastName, username, email);
        validatePassword(password);

        // Check if username or email already exists
        if (repository.findByUsername(username) != null) {
            throw new ValidationException("Username already exists.");
        }

        if (repository.findByEmail(email) != null) {
            throw new ValidationException("Email already exists.");
        }

        password = encoder.encode(password);

        AppUser appUser = new AppUser(null, firstName, lastName, username, password, email, 1);

        return repository.create(appUser);
    }

    public AppUser update(int id, String firstName, String lastName, String username, String password, String email) {
        validate(firstName, lastName, username, email);

        // Check if user exists
        AppUser existingUser = repository.findById(id);
        if (existingUser == null) {
            throw new ValidationException("User with ID " + id + " not found.");
        }

        if (username != null && !username.equals(existingUser.getUserName()) && repository.findByUsername(username) != null) {
            throw new ValidationException("Username already exists.");
        }

        if (email != null && !email.equals(existingUser.getEmail()) && repository.findByEmail(email) != null) {
            throw new ValidationException("Email already exists.");
        }

        if (password != null) {
            validatePassword(password);
            password = encoder.encode(password);
        } else {
            password = existingUser.getPasswordHash();
        }

        AppUser updatedUser = new AppUser(id, firstName, lastName, username != null ? username : existingUser.getUserName(), password, email != null ? email : existingUser.getEmail(), existingUser.getRoleId());

        return repository.update(updatedUser);
    }

    public boolean deleteById(int id) {
        // Check if user exists
        AppUser existingUser = repository.findById(id);
        if (existingUser == null) {
            throw new ValidationException("User with ID " + id + " not found.");
        }

        return repository.deleteById(id);
    }

    private void validate(String firstName, String lastName, String username, String email) {
        if (firstName == null || firstName.isBlank()) {
            throw new ValidationException("First name is required.");
        }

        if (firstName.length() < 2 || firstName.length() > 50) {
            throw new ValidationException("First name must be between 2 and 50 characters.");
        }

        if (lastName == null || lastName.isBlank()) {
            throw new ValidationException("Last name is required.");
        }

        if (lastName.length() < 2 || lastName.length() > 50) {
            throw new ValidationException("Last name must be between 2 and 50 characters.");
        }

        if (username == null || username.isBlank()) {
            throw new ValidationException("Username is required.");
        }

        if (username.length() < 3 || username.length() > 20) {
            throw new ValidationException("Username must be between 3 and 20 characters.");
        }

        if (email == null || email.isBlank()) {
            throw new ValidationException("Email is required.");
        }

        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new ValidationException("Invalid email format.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters.");
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        if (digits == 0 || letters == 0 || others == 0) {
            throw new ValidationException("Password must contain a digit, a letter, and a non-digit/non-letter.");
        }
    }
}
