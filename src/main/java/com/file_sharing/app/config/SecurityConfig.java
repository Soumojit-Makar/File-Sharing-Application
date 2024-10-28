package com.file_sharing.app.config;

import com.file_sharing.app.helper.AppCon;
import com.file_sharing.app.security.JWTAuthenticationEntryPoint;
import com.file_sharing.app.security.JWTAuthenticationFilter;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    public SecurityConfig(JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpSession httpSession) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        // cors Configuration
        http.cors(httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer.configurationSource(request ->{
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(List.of("*"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(List.of("*"));
                    config.setMaxAge(3600L);
                    return config;
                })
        );
        // Security Configuration
        http.authorizeHttpRequests(
                // URL Configuration
                authorizeRequests ->{
                    authorizeRequests
                            .requestMatchers(HttpMethod.POST, "/file/**").hasAnyRole(AppCon.ROLE_NORMAL, AppCon.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.PUT, "/file/**", "/user/**").hasAnyRole(AppCon.ROLE_NORMAL, AppCon.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.DELETE, "/file/**", "/user/**").hasAnyRole(AppCon.ROLE_NORMAL, AppCon.ROLE_ADMIN)
                            .anyRequest().permitAll();
                });
        // for any exception
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        // Stateless Session Creation Policy set
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    //Password Encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
