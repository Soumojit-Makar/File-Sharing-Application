package com.file_sharing.app.controller;

import com.file_sharing.app.dto.*;
import com.file_sharing.app.entity.UserEntity;
import com.file_sharing.app.security.JWTHelper;
import com.file_sharing.app.service.RefreshTokenService;
import com.file_sharing.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Controller", description = "Handles all authentication related actions, including user login, token generation, and token regeneration.")
public class AuthenticationController {
    private Logger logger= LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JWTHelper jwtHelper;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JWTHelper jwtHelper,
            ModelMapper modelMapper,
            UserService userService,
            RefreshTokenService refreshTokenService
            ){
        this.authenticationManager=authenticationManager;
        this.userDetailsService=userDetailsService;
        this.jwtHelper=jwtHelper;
        this.modelMapper=modelMapper;
        this.userService=userService;
        this.refreshTokenService=refreshTokenService;
    }

//     * Authenticates a user and generates a JWT token.
//
//     * This endpoint takes a JWTRequest object containing the user's email and password,
//     * authenticates the user, and generates a JWT token along with a refresh token.
@Operation(summary = "Authenticates a user and generates a JWT token")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful authentication and token generation",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = JwtResponse.class))),
        @ApiResponse(responseCode = "401", description = "Invalid username or password",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiResponseMessage.class))),
        @ApiResponse(responseCode = "404", description = "User Not Found",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiResponseMessage.class)))
})
    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        this.doAuthenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
        UserEntity user = (UserEntity) this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtHelper.generateToken(user);
        RefreshTokenDTO refreshTokenDTO= refreshTokenService.createRefreshToken(user.getEmail());
        // Return response with JWT and refresh token
        return ResponseEntity.ok(
                JwtResponse
                        .builder()
                        .refreshToken(refreshTokenDTO)
                        .JwtToken(token)
                        .user(modelMapper.map(user, UserDTo.class))
                        .build());
    }
    //Regenerates a JWT token using a valid refresh token.
    // This endpoint accepts a RefreshTokenRequest containing a refresh token,
    // verifies the token, and generates a new JWT token for the user.

    @Operation(summary = "Regenerates a JWT token using a valid refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful regeneration of JWT token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "404", description = "Invalid or expired refresh token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseMessage.class)))
    })
    @PostMapping("/regenerate-token")
    public ResponseEntity<JwtResponse>regenerateToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshTokenDTO refreshTokenDTO=refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken());
        RefreshTokenDTO refreshTokenDTO1=refreshTokenService.verifyRefreshToken(refreshTokenDTO);
        UserDTo userDTo= refreshTokenService.getUserByToken(refreshTokenDTO1);
        String jwtToken= jwtHelper.generateToken(modelMapper.map(userDTo,UserEntity.class));
        return ResponseEntity.ok(JwtResponse.builder().JwtToken(jwtToken)
                .user(userDTo)
                .refreshToken(refreshTokenDTO)
                .build());
    }
    // Helper method to perform authentication
    private void doAuthenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("User not found of given username or password");
        }
    }
}
