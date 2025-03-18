package com.rapidrescue.ambulancewale.security;

import com.rapidrescue.ambulancewale.impl.UserDetailsServiceImpl;
import com.rapidrescue.ambulancewale.service.AdminService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    Logger log = Logger.getLogger(JwtAuthFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Retrieve the Authorization header


        String authHeader = request.getHeader("Authorization");
        logger.info("Raw Authorization Header: [" + authHeader + "]");
        log.info("======================= Authorization Header: ========================{}"+authHeader );
        String token = null;
        String username = null;
        String role = null;

        // Check if the header starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim().replaceAll("[\n\r]", "");; // Extract token
            username = jwtUtil.extractUsername(token); // Extract username from token
            role = jwtUtil.extractRole(token);
        }

        log.info("Extracted Token: {}"+ token);
        log.info("Extracted Username: {}"+ username);
        log.info("Extracted role  {}"+ role);
        log.info("Authentication Before Setting: {}"+ SecurityContextHolder.getContext().getAuthentication());



        // If the token is valid and no authentication is set in the context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUserId(username);
        log.info("============================== user Details ====================" + userDetails.getAuthorities());
            // Validate token and set authentication
            if (jwtUtil.validateToken(token, userDetails)) {
                log.info("============================== validateToken " +jwtUtil.validateToken(token, userDetails)+"===================================");

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                log.info("============================== UsernamePasswordAuthenticationToken ===================="+token);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chaind
        filterChain.doFilter(request, response);
    }

}
