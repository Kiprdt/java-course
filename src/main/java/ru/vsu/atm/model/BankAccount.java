package ru.vsu.atm.model;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long balance = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public void deposit(long amount) { this.balance += amount; }
    public void withdraw(long amount) { this.balance -= amount; }

    public long getBalance() { return balance; }

    public abstract long getAvailableBalance();
    public abstract AccountType getAccountType();
}
