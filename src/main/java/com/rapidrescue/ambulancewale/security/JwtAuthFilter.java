package com.rapidrescue.ambulancewale.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rapidrescue.ambulancewale.impl.UserDetailsServiceImpl;
import com.rapidrescue.ambulancewale.service.AdminService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    Logger log = Logger.getLogger(JwtAuthFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            log.info("=======================Raw Authorization Header: [{}]=========================\n"+ authHeader);
            log.info("============"+request.getRequestURI() );

            String token = null;
            String username = null;
            String role = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7).trim().replace("\n", "").replace("\r", ""); // Clean token
                username = jwtUtil.extractUsername(token);
                role = jwtUtil.extractRole(token);
            }

            log.info("Extracted Token: {}"+ token);
            log.info("Extracted Username: {}"+ username);
            log.info("Extracted Role: {}"+ role);
            log.info("Authentication Before Setting: {}"+ SecurityContextHolder.getContext().getAuthentication());

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                log.info("User Details Authorities: {}"+ userDetails.getAuthorities());

                boolean isValid = jwtUtil.validateToken(token, userDetails);
                log.info("Is Token Valid: {}"+ isValid);

                if (isValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            log.warning("JWT Token has expired: " + e.getMessage());
            sendErrorResponse(response, "JWT_EXPIRED", "Your session has expired. Please login again.", 401);
        } catch (MalformedJwtException e) {
            log.warning("Malformed JWT Token: " + e.getMessage());
            sendErrorResponse(response, "JWT_MALFORMED", "Invalid token format.", 403);
        } catch (Exception e) {
            log.warning("Unexpected error in JWT Filter: " + e.getMessage());
            sendErrorResponse(response, "JWT_ERROR", "Invalid token or authentication issue.", 403);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String errorCode, String message, int status)
            throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("errorCode", errorCode);
        errorDetails.put("message", message);
        errorDetails.put("timestamp", System.currentTimeMillis());

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
        response.getWriter().flush();
    }


}
