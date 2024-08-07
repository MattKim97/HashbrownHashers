package learn.hashbrown_hashers.controllers;

import learn.hashbrown_hashers.domain.Result;
import learn.hashbrown_hashers.domain.UserService;
import learn.hashbrown_hashers.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable Integer userId) {
        return userService.findById(userId);
    }

    @PostMapping
    public Result<User> add(@RequestBody User user) {
        return userService.add(user);
    }

    @PutMapping("/{userId}")
    public Result<User> update(@PathVariable Integer userId, @RequestBody User user, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        user.setUserId(userId);
        return userService.updateUser(currentUser, user);
    }

    @DeleteMapping("/{userId}")
    public Result<Void> deleteById(@PathVariable Integer userId, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return userService.deleteById(userId, currentUser);
    }
}
