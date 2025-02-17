package com.file_sharing.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request body for JWT authentication containing username and password.")
public class JwtRequest {
    @Schema(description = "The username for authentication", example = "user123@gmail.com")
    private String username;
    @Schema(description = "The password associated with the username", example = "password123")
    private String password;
}
