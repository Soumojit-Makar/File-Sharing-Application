package com.file_sharing.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.file_sharing.app.dto.ApiResponseMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Set the response status to 401 Unauthorized when authentication fails.
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // Initialize ObjectMapper to convert the response into JSON format.
        ObjectMapper mapper = new ObjectMapper();
        // Create a JSON response using the ApiResponseMessage class.
        // This sets the success flag to false, adds the exception message, and sets the HTTP status.
        String json = mapper.writeValueAsString( ApiResponseMessage.builder()
                .success(false)
                .message(authException.getMessage())
                .httpStatus(HttpStatus.FORBIDDEN)
                .build());
        // Set the content type of the response to JSON.
        response.setContentType("application/json");
        // Write the JSON response to the output stream.
        response.getWriter().write(json);
    }
}
