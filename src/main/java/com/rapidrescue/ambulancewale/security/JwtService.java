//package com.rapidrescue.ambulancewale.security;
//
//import com.rapidrescue.ambulancewale.impl.UserDetailsImpl;
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Component
//public class JwtUtil {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
//
//    @Value("${app.jwtSecret}")
//    private String jwtSecret; // Ensure this is Base64 encoded in application.properties
//
//    @Value("${app.jwtExpirationMs}")
//    private long jwtExpirationMs;
//
//    @Value("${app.jwtRefreshExpirationMs}")
//    private long jwtRefreshExpirationMs;
//
//    private SecretKey key;
//
//    @PostConstruct
//    public void init() {
//        if (jwtSecret.length() < 32) {
//            throw new IllegalArgumentException("JWT Secret Key must be at least 32 bytes long.");
//        }
//        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)); // Properly decode Base64 secret key
//    }
//
//    public String generateToken(Authentication authentication) {
//        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//        return buildToken(userPrincipal.getUsername(), userPrincipal);
//    }
//
//    public String generateRefreshToken(UserDetails userDetails) {
//        return buildToken(userDetails.getUsername(), null);
//    }
//
//    private String buildToken(String subject, UserDetails userDetails) {
//        return Jwts.builder()
//                .subject(subject)
//                .claim("role", userDetails != null ? userDetails.getAuthorities().stream()
//                        .map(GrantedAuthority::getAuthority)
//                        .collect(Collectors.joining(",")) : "REFRESH")
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + (userDetails != null ? jwtExpirationMs : jwtRefreshExpirationMs)))
//                .signWith(key)
//                .compact();
//    }
//
//    public String extractUserId(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public boolean validateJwtToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (MalformedJwtException e) {
//            logger.error("JWT token is malformed: {}", e.getMessage());
//        } catch (SignatureException e) {
//            logger.error("JWT signature is invalid: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty: {}", e.getMessage());
//        }
//        return false;
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUserId(token);
//        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
//
//    public long getRefreshExpirationInMs() {
//        return jwtRefreshExpirationMs;
//    }
//}
