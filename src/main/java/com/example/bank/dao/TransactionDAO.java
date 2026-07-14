package com.example.bank.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.bank.dto.TransactionDepositDTO;
import com.example.bank.dto.TransactionResponseDTO;
import com.example.bank.dto.TransactionTransferDTO;
import com.example.bank.dto.TransactionWithdrawDTO;
import com.example.bank.type.TransactionType;

public class TransactionDAO {
    public TransactionResponseDTO getTransactionById(Connection conn, Long id) throws SQLException {
        String sql = "SELECT id, account_id, account_id_transfer, type, amount, balance_after, created_at FROM transactions WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    TransactionResponseDTO transaction = new TransactionResponseDTO(
                        rs.getLong("id"), 
                        rs.getString("account_id"), 
                        rs.getString("account_id_transfer"), 
                        TransactionType.valueOf(rs.getString("type")), 
                        rs.getLong("amount"), 
                        rs.getLong("balance_after"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    return transaction;
                }
            }
        }
        return null;
    }

    public List<TransactionResponseDTO> getTransactionsByAccountId(Connection conn, String accountId) throws SQLException {
        String sql = "SELECT id, account_id, account_id_transfer, type, amount, balance_after, created_at FROM transactions WHERE account_id = ? ORDER BY created_at DESC";
        List<TransactionResponseDTO> transactions = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TransactionResponseDTO transaction = new TransactionResponseDTO(
                        rs.getLong("id"), 
                        rs.getString("account_id"), 
                        rs.getString("account_id_transfer"), 
                        TransactionType.valueOf(rs.getString("type")), 
                        rs.getLong("amount"), 
                        rs.getLong("balance_after"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }

    public boolean createDepositTransaction(Connection conn, TransactionDepositDTO transaction) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, type, amount, balance_after) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, transaction.getAccountId());
            pstmt.setString(2, "DEPOSIT");
            pstmt.setLong(3, transaction.getAmount());
            pstmt.setLong(4, transaction.getBalance() + transaction.getAmount());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean createWithdrawTransaction(Connection conn, TransactionWithdrawDTO transaction) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, type, amount, balance_after) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, transaction.getAccountId());
            pstmt.setString(2, "WITHDRAW");
            pstmt.setLong(3, -transaction.getAmount());
            pstmt.setLong(4, transaction.getBalance() - transaction.getAmount());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean createTransferTransaction(Connection conn, TransactionTransferDTO transaction) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, account_id_transfer, group_id_transfer, type, amount, balance_after) VALUES (?, ?, LPAD(transaction_group_id_seq.NEXTVAL, 8, '0'), ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, transaction.getAccountId());
            pstmt.setString(2, transaction.getAccountIdTransfer());
            pstmt.setString(3, "TRANSFER_OUT");
            pstmt.setLong(4, -transaction.getAmount());
            pstmt.setLong(5, transaction.getBalance() - transaction.getAmount());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows <= 0) {
                return false;
            }
        }

        sql = "INSERT INTO transactions (account_id, account_id_transfer, group_id_transfer, type, amount, balance_after) VALUES (?, ?, LPAD(transaction_group_id_seq.CURRVAL, 8, '0'), ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, transaction.getAccountIdTransfer());
            pstmt.setString(2, transaction.getAccountId());
            pstmt.setString(3, "TRANSFER_IN");
            pstmt.setLong(4, transaction.getAmount());
            pstmt.setLong(5, transaction.getBalanceTransfer() + transaction.getAmount());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows <= 0) {
                return false;
            }
        }

        return true;
    }
}
