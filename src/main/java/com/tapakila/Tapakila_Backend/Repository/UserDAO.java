package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO {
    boolean existsByUserEmail(String email);
    Optional<User> getUserByEmail(String email);
    public void save(User user);
}
