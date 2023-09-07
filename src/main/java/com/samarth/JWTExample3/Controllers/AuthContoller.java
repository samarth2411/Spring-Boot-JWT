package com.samarth.JWTExample3.Controllers;

import com.samarth.JWTExample3.Service.RefreshTokenService;
import com.samarth.JWTExample3.Service.UserService;
import com.samarth.JWTExample3.modal.*;
import com.samarth.JWTExample3.security.JWTHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthContoller {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private Logger logger = LoggerFactory.getLogger(AuthContoller.class);

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        JWTResponse response = JWTResponse.builder()
                .jwtToken(token)
                .refreshToken(refreshToken.getRefreshToken())
                .username(userDetails.getUsername()).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        try{
            manager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException e){
            throw new RuntimeException("Invalid UserName and password !!");
        }
    }
    @PostMapping("/refresh")
    public JWTResponse refreshJwtToken(@RequestBody RefreshTokenRequest request){

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        User user = refreshToken.getUser();

        String token = this.jwtHelper.generateToken(user);

        return JWTResponse.builder().refreshToken(refreshToken.getRefreshToken())
                .jwtToken(token)
                .username(user.getEmail())
                .build();
    }


    @PostMapping("/create-user")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
}
