package com.tapakila.Tapakila_Backend.Entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetail implements UserDetails {
    private String email;
    private String password;
    private String userType;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetail(String email, String password, String userType,
                            Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
