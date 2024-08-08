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

    @PostMapping("/current-user")
    public ResponseEntity<AppUser> getCurrentUser(@RequestBody String username) {
        AppUser user = userService.findByUserName(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(user);
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> credentials) {
//        System.out.println("Authenticating user: " + credentials.get("username"));
//
//
//        UsernamePasswordAuthenticationToken authToken =
//                new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"));
//
//        try {
//            Authentication authentication = authenticationManager.authenticate(authToken);
//            System.out.println("Authentication successful: " + authentication.isAuthenticated());
//
//            if (authentication.isAuthenticated()) {
//                String jwtToken = jwtConverter.getTokenFromUser((User) authentication.getPrincipal());
//
//
//                Map<String, String> response = new HashMap<>();
//                response.put("jwt_token", jwtToken);
//                System.out.println("JWT Token generated: " + jwtToken);
//
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//
//        } catch (AuthenticationException ex) {
//            System.out.println("Authentication failed: " + ex.getMessage());
//
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        System.out.println("Authentication failed: User not authenticated");
//
//        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> credentials) {
        System.out.println("Authenticating user: " + credentials.get("username"));

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"));

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);
            System.out.println("Authentication successful: " + authentication.isAuthenticated());

            if (authentication.isAuthenticated()) {
                String jwtToken = jwtConverter.getTokenFromUser((User) authentication.getPrincipal());

                // Fetch user details
                AppUser user = userService.findByUserName(credentials.get("username"));
                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }

                System.out.println(user.getUserId());

                // Create response map
                Map<String, String> response = new HashMap<>();
                response.put("jwt_token", jwtToken);
                response.put("user_id", String.valueOf(user.getUserId()));

                System.out.println("JWT Token generated: " + jwtToken);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (AuthenticationException ex) {
            System.out.println("Authentication failed: " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        System.out.println("Authentication failed: User not authenticated");
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
