package com.example.bank.dto;

import java.time.LocalDateTime;
import com.example.bank.type.TransactionType;

public class TransactionResponseDTO {
    private Long id;
    private String accountId;
    private String accountIdTransfer;
    private TransactionType type;
    private Long amount;
    private Long balanceAfter;
    private LocalDateTime createdAt;

    public TransactionResponseDTO() {}

    public TransactionResponseDTO(Long id, String accountId, String accountIdTransfer, TransactionType type, Long amount, Long balanceAfter, LocalDateTime createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.accountIdTransfer = accountIdTransfer;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountIdTransfer() {
        return accountIdTransfer;
    }

    public void setAccountIdTransfer(String accountIdTransfer) {
        this.accountIdTransfer = accountIdTransfer;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Long balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
