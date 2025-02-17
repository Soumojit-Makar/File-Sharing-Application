package com.file_sharing.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
@Schema(description = "Response containing JWT token, user details, and refresh token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    @Schema(description = "The JWT token issued for the authenticated user", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String JwtToken;
    @Schema(description = "Details of the authenticated user", implementation = UserDTo.class)
    private UserDTo user;
    @Schema(description = "The refresh token associated with the JWT token", implementation = RefreshTokenDTO.class)
    private RefreshTokenDTO refreshToken;

}
