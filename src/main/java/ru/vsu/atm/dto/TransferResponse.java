package ru.vsu.atm.dto;

public class TransferResponse {
    private Long fromAccountId;
    private Long toAccountId;
    private long amount;
    private String status;

    public TransferResponse(Long fromAccountId, Long toAccountId, long amount, String status) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.status = status;
    }
    public Long getFromAccountId() { return fromAccountId; }
    public Long getToAccountId() { return toAccountId; }
    public long getAmount() { return amount; }
    public String getStatus() { return status; }
}
