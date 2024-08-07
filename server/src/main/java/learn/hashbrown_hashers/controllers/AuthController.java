package learn.hashbrown_hashers.controllers;

import learn.hashbrown_hashers.models.AppUser;
import learn.hashbrown_hashers.security.JwtConverter;
import learn.hashbrown_hashers.security.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtConverter jwtConverter;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtConverter jwtConverter, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtConverter = jwtConverter;
        this.userService = userService;
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserDetails> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userDetails);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> credentials) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"));

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                String jwtToken = jwtConverter.getTokenFromUser((User) authentication.getPrincipal());

                Map<String, String> response = new HashMap<>();
                response.put("jwt_token", jwtToken);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> credentials) {
        AppUser appUser;

        try {
            String firstName = credentials.get("firstName");
            String lastName = credentials.get("lastName");
            String username = credentials.get("username");
            String password = credentials.get("password");
            String email = credentials.get("email");

            appUser = userService.create(firstName, lastName, username, password, email);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(List.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (DuplicateKeyException ex) {
            return new ResponseEntity<>(List.of("The provided username or email already exists"), HttpStatus.BAD_REQUEST);
        }

        Map<String, Integer> response = new HashMap<>();
        response.put("userId", appUser.getUserId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
