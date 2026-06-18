package ru.vsu.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.atm.model.BankAccount;

public interface AccountRepository extends JpaRepository<BankAccount, Long> {
}
