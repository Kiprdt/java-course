package ru.vsu.atm.service;

import org.springframework.stereotype.Service;
import ru.vsu.atm.exception.ValidationException;
import ru.vsu.atm.model.User;
import ru.vsu.atm.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String login, String password) {
        if (userRepository.findByLogin(login) != null) {
            throw new ValidationException("Логин уже занят.");
        }
        User user = new User(login, password);
        return userRepository.save(user);
    }

    public User login(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user == null || !user.getPassword().equals(password)) {
            throw new ValidationException("Неверный логин или пароль.");
        }
        return user;
    }

    public User getUserById(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) throw new ValidationException("Пользователь не найден.");
        return user;
    }
}