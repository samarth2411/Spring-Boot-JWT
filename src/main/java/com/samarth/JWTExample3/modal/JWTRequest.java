package com.samarth.JWTExample3.modal;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JWTRequest {

    private String email;

    private String password;
}
