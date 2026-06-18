package ru.vsu.atm.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.atm.exception.ValidationException;
import ru.vsu.atm.model.User;
import ru.vsu.atm.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String login, String password) {
        if (userRepository.existsByLogin(login)) {
            throw new ValidationException("Логин уже занят.");
        }
        User user = new User(login, passwordEncoder.encode(password));
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    public User login(String login, String password) {
        User user = userRepository.findByLogin(login).orElse(null);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new ValidationException("Неверный логин или пароль.");
        }
        return user;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException("Пользователь не найден."));
    }
}
