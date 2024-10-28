package com.file_sharing.app.dto;

import lombok.*;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenDTO {
    private int id;
    private String refreshTokenHold;
    private Instant expiresDate;
    private UserDTo user;
}
