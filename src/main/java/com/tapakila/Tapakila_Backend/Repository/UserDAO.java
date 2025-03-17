package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {
    boolean existsByUserEmail(String email);
    User getUserByEmail(String email);
    public void save(User user);
}
