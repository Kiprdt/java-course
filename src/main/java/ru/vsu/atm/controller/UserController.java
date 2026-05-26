package ru.vsu.atm.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.atm.dto.*;
import ru.vsu.atm.model.User;
import ru.vsu.atm.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody AuthRequest request) {
        User user = userService.register(request.getLogin(), request.getPassword());
        return new UserResponse(user.getId(), user.getLogin());
    }
}