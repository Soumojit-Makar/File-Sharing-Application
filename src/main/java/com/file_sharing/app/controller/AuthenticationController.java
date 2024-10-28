package com.file_sharing.app.controller;

import com.file_sharing.app.dto.JwtRequest;
import com.file_sharing.app.dto.JwtResponse;
import com.file_sharing.app.dto.RefreshTokenDTO;
import com.file_sharing.app.dto.UserDTo;
import com.file_sharing.app.entity.UserEntity;
import com.file_sharing.app.security.JWTHelper;
import com.file_sharing.app.service.RefreshTokenService;
import com.file_sharing.app.service.UserService;
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
    // Helper method to perform authentication
    private void doAuthenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("User not found of given username or password");
        }
    }
}
