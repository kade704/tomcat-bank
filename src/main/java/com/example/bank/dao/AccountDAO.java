package com.example.bank.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.bank.dto.AccountResponseDTO;

public class AccountDAO {
    public AccountResponseDTO getAccountById(Connection conn, String id) throws SQLException {
        String sql = "SELECT id, balance, created_at FROM accounts WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new AccountResponseDTO(
                        rs.getString("id"), 
                        rs.getLong("balance"), 
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public Long getAccountBalanceById(Connection conn, String id) throws SQLException {
        String sql = "SELECT balance FROM accounts WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("balance");
                } else {
                    throw new SQLException("Account not found: " + id);
                }
            }
        }
    }

    public List<AccountResponseDTO> getAccountsByUserId(Connection conn, String userId) throws SQLException {
        String sql = "SELECT id, balance, created_at FROM accounts WHERE user_id = ? AND deleted_at IS NULL";
        List<AccountResponseDTO> accounts = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AccountResponseDTO account = new AccountResponseDTO(
                        rs.getString("id"), 
                        rs.getLong("balance"), 
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    accounts.add(account);
                }
            }
        }
        return accounts;
    }

    public boolean createAccount(Connection conn, String userId, String branchId) throws SQLException {
        final String BANK_CODE = "999"; // 은행 코드

        String accountIDPrefix = String.format("%s-%s-", BANK_CODE, branchId);

        String sql = "INSERT INTO accounts (id, user_id) VALUES (? || LPAD(account_id_seq.NEXTVAL, 6, '0'), ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, accountIDPrefix);
            pstmt.setString(2, userId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean deleteAccount(Connection conn, String id) throws SQLException {
        String sql = "UPDATE accounts SET deleted_at = CURRENT_TIMESTAMP WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean increaseAccountBalance(Connection conn, String id, Long amount) throws SQLException {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, amount);
            pstmt.setString(2, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean decreaseAccountBalance(Connection conn, String id, Long amount) throws SQLException {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE id = ? AND balance >= ? AND deleted_at IS NULL";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, amount);
            pstmt.setString(2, id);
            pstmt.setLong(3, amount);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean accountExists(Connection conn, String id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM accounts WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
