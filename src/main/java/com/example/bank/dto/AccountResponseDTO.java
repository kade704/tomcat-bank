package com.example.bank.dto;

import java.time.LocalDateTime;

public class AccountResponseDTO {
    private String id;
    private Long balance;
    private LocalDateTime createdAt;

    public AccountResponseDTO() {}

    public AccountResponseDTO(String id, Long balance, LocalDateTime createdAt) {
        this.id = id;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
