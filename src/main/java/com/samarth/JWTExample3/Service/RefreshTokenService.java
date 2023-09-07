package com.samarth.JWTExample3.Service;

import com.samarth.JWTExample3.modal.RefreshToken;
import com.samarth.JWTExample3.modal.User;
import com.samarth.JWTExample3.repo.RefreshTokenRepo;
import com.samarth.JWTExample3.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    public long refreshTokenValidity = 2*60*1000;   // validity of refresh token is for 5 hrs

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    private UserRepo userRepository;

    public RefreshToken createRefreshToken(String userName) {

        User user = userRepository.findByEmail(userName).get();
        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken == null){
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();

        }
        else{
            refreshToken.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
        }

        user.setRefreshToken(refreshToken);
        // saving to db
        refreshTokenRepo.save(refreshToken);

        return refreshToken;
    }


    public RefreshToken verifyRefreshToken(String refreshToken) {

       RefreshToken refreshTokenObject = refreshTokenRepo.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Token Does not exists in db !!"));

       if(refreshTokenObject.getExpiry().compareTo(Instant.now())<0){
           refreshTokenRepo.delete(refreshTokenObject);
           throw new RuntimeException("Refresh Token is Expired !!");
       }
        return refreshTokenObject;
    }
}
