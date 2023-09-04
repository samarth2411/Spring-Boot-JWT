package com.samarth.JWTExample3.Service;

import com.samarth.JWTExample3.modal.User;
import com.samarth.JWTExample3.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // load user from database
        User user = userRepo.findByEmail(username).orElseThrow(()->new RuntimeException("User Not Found Exception"));

        return user;
    }
}
