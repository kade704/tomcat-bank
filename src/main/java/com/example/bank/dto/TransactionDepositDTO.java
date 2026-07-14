package com.example.bank.dto;

public class TransactionDepositDTO {
    private String accountId;
    private Long amount;
    private Long balance;

    public TransactionDepositDTO() {}

    public TransactionDepositDTO(String accountId, Long amount, Long balance) {
        this.accountId = accountId;
        this.amount = amount;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
