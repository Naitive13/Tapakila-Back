package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {
    boolean existsByUserEmail(String email);
    User getUserByEmail(String email);
    User getUserByUUID(String UUID);
    List<User> getAll();
    void save(User user);
    void delete(String userID);
}
