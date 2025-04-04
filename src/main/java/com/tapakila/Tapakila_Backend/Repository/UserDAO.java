package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Enum.UserType;
import com.tapakila.Tapakila_Backend.Entity.User;
import com.tapakila.Tapakila_Backend.Entity.UserVariant.UpdateRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {
    boolean existsByUserEmail(String email);
    User getUserByEmail(String email);
    User getUserByUUID(String UUID);
    List<User> getAll(int pageSize, int pageNumber);
    void save(User user);
    void delete(String userID);
    void updateUser(UpdateRequest userUpdateBody);
    void updateUserType(String id,UserType type);
}
