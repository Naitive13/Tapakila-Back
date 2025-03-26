package com.tapakila.Tapakila_Backend.Services;


import com.tapakila.Tapakila_Backend.Entity.UserVariant.CustomUserDetail;
import com.tapakila.Tapakila_Backend.Entity.User;
import com.tapakila.Tapakila_Backend.Entity.Enum.UserType;
import com.tapakila.Tapakila_Backend.Repository.UserDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    private UserDAOImpl userRepository;

    @Override
    public CustomUserDetail loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userRepository.getUserByEmail(email);

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (user.getType() == UserType.admin) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_STANDARD"));
            }

            return new CustomUserDetail(
                    user.getEmail(),
                    user.getPassword(),
                    user.getType().toString(),
                    authorities
            );
        } catch (Exception e){
            logger.error("Error loading user by email: " + email, e);
            throw new UsernameNotFoundException("Error loading user by email: " + email, e);
        }
    }
}
