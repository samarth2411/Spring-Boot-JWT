package com.samarth.JWTExample3.repo;

import com.samarth.JWTExample3.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

    public Optional<User> findByEmail(String email);
}
