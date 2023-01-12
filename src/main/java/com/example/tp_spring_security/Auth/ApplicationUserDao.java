package com.example.tp_spring_security.Auth;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ApplicationUserDao {
    public Optional<ApplicationUser> SelectApplicationUserByUsername
            (String username);
}
