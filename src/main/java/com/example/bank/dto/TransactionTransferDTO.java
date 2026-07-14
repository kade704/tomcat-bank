package com.example.bank.dto;

public class TransactionTransferDTO {
    private String accountId;
    private String accountIdTransfer;
    private Long amount;
    private Long balance;
    private Long balanceTransfer;

    public TransactionTransferDTO() {}

    public TransactionTransferDTO(String accountId, String accountIdTransfer, Long amount, Long balance, Long balanceTransfer) {
        this.accountId = accountId;
        this.accountIdTransfer = accountIdTransfer;
        this.amount = amount;
        this.balance = balance;
        this.balanceTransfer = balanceTransfer;
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

    public Long getBalanceTransfer() {
        return balanceTransfer;
    }

    public void setBalanceTransfer(Long balanceTransfer) {
        this.balanceTransfer = balanceTransfer;
    }
    
}
