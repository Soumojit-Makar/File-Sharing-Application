package com.file_sharing.app.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final Logger logger= LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private final JWTHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    public JWTAuthenticationFilter(final JWTHelper jwtHelper, final UserDetailsService userDetailsService) {
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

     //Processes the incoming request, extracts the JWT token from the Authorization header,
     // and performs authentication if the token is valid.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        logger.info("Request Header: {}", requestHeader);
        String username = null;
        String token = null;

        // Validate the Authorization header and extract the token
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            try {
                username = jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token: {}", e.getMessage());
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("\"error\" : \"Token expired\"}");
                return;
            } catch (MalformedJwtException e) {
                logger.error("JWT Token is malformed: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("JWT Token processing error: {}", e.getMessage());
            }
        } else {
            logger.error("Authorization header missing or does not start with Bearer");
        }

        // Authenticate the user if the token is valid
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Validate token
            if (jwtHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);

    }
}
