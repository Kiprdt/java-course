package ru.vsu.atm.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.atm.dto.*;
import ru.vsu.atm.model.User;
import ru.vsu.atm.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody AuthRequest request) {
        User user = userService.login(request.getLogin(), request.getPassword());
        return new LoginResponse(user.getId(), user.getLogin());
    }
}