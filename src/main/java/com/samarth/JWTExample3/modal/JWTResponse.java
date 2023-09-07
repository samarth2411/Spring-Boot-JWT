package com.samarth.JWTExample3.modal;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JWTResponse {

    private String jwtToken;

    private String refreshToken;

    private String username;

}
