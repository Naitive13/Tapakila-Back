package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.User;
import com.tapakila.Tapakila_Backend.Entity.Enum.UserType;
import com.tapakila.Tapakila_Backend.Entity.UserVariant.UpdateRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private final Datasource datasource;
    private final PasswordEncoder passwordEncoder = new  BCryptPasswordEncoder();

    public UserDAOImpl(Datasource datasource) {
        this.datasource = datasource;
    }


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
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE user_email = ?";
        User user = new User();
        try (Connection conn = datasource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getString("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("user_email"));
                user.setPassword(rs.getString("user_password"));
                user.setType(UserType.valueOf(rs.getString("user_type")));
                user.setCreationDate(rs.getTimestamp("user_creation_date").toInstant());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User getUserByUUID(String UUID) {
        User user = new User();
        String sql = "SELECT user_id::TEXT AS user_id, user_email, user_name, user_type, user_creation_date FROM users where user_id = ?::UUID";
        try (Connection conn = datasource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, UUID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getString("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("user_email"));
                user.setType(UserType.valueOf(rs.getString("user_type")));
                user.setCreationDate(rs.getTimestamp("user_creation_date").toInstant());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> getAll(int pageSize, int pageNumber) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT user_id::TEXT AS user_id, user_email, user_name, user_type, user_creation_date FROM users LIMIT ?  OFFSET ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, (pageNumber * (pageSize - 1)));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("user_email"));
                user.setType(UserType.valueOf(rs.getString("user_type")));
                user.setCreationDate(rs.getTimestamp("user_creation_date").toInstant());
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (user_id, user_name, user_email, user_password, user_type, user_creation_date) VALUES (?::UUID, ?, ?, ?, ?::user_type, ?::TIMESTAMP)";
        try (Connection conn = datasource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setObject(1, user.getUserId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, passwordEncoder.encode(user.getPassword()));
            preparedStatement.setString(5, String.valueOf(user.getType()));
            preparedStatement.setString(6, String.valueOf(user.getCreationDate()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void updateUser(UpdateRequest userUpdateBody) {
        String sql =
                """
                        UPDATE users SET user_email = ?, user_name=?, user_password=? where user_id = ?::UUID
                        """;
        String password = userUpdateBody.getUserPassword();
        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, userUpdateBody.getEmail());
            preparedStatement.setString(2, userUpdateBody.getUserName());
            preparedStatement.setString(3, passwordEncoder.encode(password));
            preparedStatement.setString(4, userUpdateBody.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(String userID) {
        String sql = "DELETE FROM users where user_id = ?::UUID";

        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
