package com.samarth.JWTExample3.repo;

import com.samarth.JWTExample3.modal.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,String> {

    Optional<RefreshToken> findByRefreshToken(String token);


}
