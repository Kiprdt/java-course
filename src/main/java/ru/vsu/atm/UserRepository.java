package ru.vsu.atm;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public User findByLogin(String login) {
        return users.get(login);
    }

    public void save(User user) {
        users.put(user.getLogin(), user);
    }
}