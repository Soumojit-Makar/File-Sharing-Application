package com.file_sharing.app.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String JwtToken;
    private UserDTo user;
    private RefreshTokenDTO refreshToken;

}
