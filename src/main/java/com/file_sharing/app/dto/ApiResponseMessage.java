package com.file_sharing.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Schema(description = "Standard API response message containing the result of an operation, message, and HTTP status.")
@Data
@Builder
public class ApiResponseMessage {
    @Schema(description = "Message describing the outcome of the operation", example = "File uploaded successfully")
    private String message;
    @Schema(description = "Indicates whether the operation was successful", example = "true")
    private boolean success;
    @Schema(description = "HTTP status code of the response", example = "200", implementation = HttpStatus.class)
    private HttpStatus httpStatus;
}
