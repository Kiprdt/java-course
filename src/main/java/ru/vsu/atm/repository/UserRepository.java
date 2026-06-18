package ru.vsu.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.atm.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
}
