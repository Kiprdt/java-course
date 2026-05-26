package ru.vsu.atm.dto;

public class SummaryResponse {
    private Long userId;
    private long totalBalance;
    private long totalAvailableBalance;
    private int accountsCount;

    public SummaryResponse(Long userId, long totalBalance, long totalAvailableBalance, int accountsCount) {
        this.userId = userId;
        this.totalBalance = totalBalance;
        this.totalAvailableBalance = totalAvailableBalance;
        this.accountsCount = accountsCount;
    }
    // Геттеры
    public Long getUserId() { return userId; }
    public long getTotalBalance() { return totalBalance; }
    public long getTotalAvailableBalance() { return totalAvailableBalance; }
    public int getAccountsCount() { return accountsCount; }
}