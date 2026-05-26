package ru.vsu.atm;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String login, String password) {
        if (userRepository.findByLogin(login) != null) {
            throw new ValidationException("Пользователь с таким логином уже существует.");
        }
        User newUser = new User(login, password);
        userRepository.save(newUser);
        return newUser;
    }

    public User login(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new ValidationException("Пользователь не найден.");
        }
        if (!user.getPassword().equals(password)) {
            throw new ValidationException("Неверный пароль.");
        }
        return user;
    }
}