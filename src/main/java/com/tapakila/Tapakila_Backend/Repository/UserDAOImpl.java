package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private Datasource datasource;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean existsByUserEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_email = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE user_email = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(UUID.fromString(rs.getString("user_id")));
                user.setUserName(rs.getString("user_name"));
                user.setUserEmail(rs.getString("user_email"));
                user.setUserPassword(rs.getString("user_password"));
                user.setUserType(rs.getString("user_type"));
                user.setCreationDate(rs.getString("creation_date"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (user_id, user_name, user_email, user_password, user_type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = datasource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setObject(1, user.getUserId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getUserEmail());
            preparedStatement.setString(4, passwordEncoder.encode(user.getUserPassword())); // Hash the password
            preparedStatement.setString(5, user.getUserType());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
