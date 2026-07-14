package com.example.bank.dao;

import java.sql.*;

import com.example.bank.dto.UserLoginDTO;
import com.example.bank.dto.UserResponseDTO;
import com.example.bank.dto.UserSignupDTO;
import com.example.bank.util.PasswordUtil;

public class UserDAO {

    public UserResponseDTO getUserById(Connection conn, String id) throws SQLException {
        String sql = "SELECT id, email, full_name, phone_number, age, created_at FROM users WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UserResponseDTO user = new UserResponseDTO(
                        rs.getString("id"),
                        rs.getString("full_name"), 
                        rs.getString("email"), 
                        rs.getString("phone_number"), 
                        rs.getInt("age"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    return user;
                }
            }
        }
        return null;
    }

    public boolean loginUser(Connection conn, UserLoginDTO user) throws SQLException {
        String sql = "SELECT password_hash, password_salt FROM users WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getId());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password_hash");
                    byte[] salt = rs.getBytes("password_salt");
                    String hashedInputPassword = PasswordUtil.getSHA256(user.getPassword(), salt);
                    return hashedPassword.equals(hashedInputPassword);
                }
            }
        }
        return false;
    }

    public boolean signupUser(Connection conn, UserSignupDTO user) throws SQLException {
        String sql = "INSERT INTO users (id, password_hash, password_salt, full_name, email, phone_number, age) VALUES (?, ?, ?, ?, ?, ?, ?)";

        byte[] salt = PasswordUtil.getSalt();
        String hashedPassword = PasswordUtil.getSHA256(user.getPassword(), salt);

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getId());
            pstmt.setString(2, hashedPassword);
            pstmt.setBytes(3, salt);
            pstmt.setString(4, user.getFullName());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getPhoneNumber());
            pstmt.setInt(7, user.getAge());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }
        }
        return false;
    }
}
