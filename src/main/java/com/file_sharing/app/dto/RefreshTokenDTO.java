package com.file_sharing.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO representing a refresh token with expiration and associated user information.")
public class RefreshTokenDTO {
    @Schema(description = "The unique identifier for the refresh token", example = "1")
    private int id;
    @Schema(description = "The refresh token string that is used to obtain a new access token", example = "abcd1qrr234")
    private String refreshTokenHold;
    @Schema(description = "The expiration date and time of the refresh token", example = "2025-12-31T23:59:59Z")
    private Instant expiresDate;
    @Schema(description = "The user associated with this refresh token", implementation = UserDTo.class)
    private UserDTo user;
}
