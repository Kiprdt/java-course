package ru.vsu.atm.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import ru.vsu.atm.model.AccountType;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {
    private Long id;
    private AccountType type;
    private long balance;
    private Long creditLine;
    private long availableBalance;

 
    public AccountResponse(Long id, AccountType type, long balance, Long creditLine, long availableBalance) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.creditLine = creditLine;
        this.availableBalance = availableBalance;
    }
    public Long getId() { return id; }
    public AccountType getType() { return type; }
    public long getBalance() { return balance; }
    public Long getCreditLine() { return creditLine; }
    public long getAvailableBalance() { return availableBalance; }
}
