package com.samarth.JWTExample3.modal;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    private String refreshToken; // this will be unique

    private Instant expiry;

    @OneToOne
    private User user;
}
