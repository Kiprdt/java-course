package ru.vsu.atm.repository;

import org.springframework.stereotype.Repository;
import ru.vsu.atm.model.User;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 1;

    public User save(User user) {
        user.setId(idCounter++);
        users.put(user.getId(), user);
        return user;
    }

    public User findByLogin(String login) {
        for (User user : users.values()) {
            if (user.getLogin().equals(login)) return user;
        }
        return null;
    }

    public User findById(Long id) {
        return users.get(id);
    }
}