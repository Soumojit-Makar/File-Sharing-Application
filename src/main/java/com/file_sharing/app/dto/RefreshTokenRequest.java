package com.file_sharing.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor@NoArgsConstructor
@Data
@Schema(description = "DTO representing a request to refresh an access token using a refresh token.")
public class RefreshTokenRequest {
    @Schema(description = "The refresh token used to obtain a new access token", example = "abcd1234")
    private String refreshToken;

}