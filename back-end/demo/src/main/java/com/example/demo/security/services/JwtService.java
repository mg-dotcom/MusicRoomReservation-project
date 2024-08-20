package com.example.demo.security.services;

import com.example.demo.entities.user.User;
import com.example.demo.entities.user.Role;
import com.example.demo.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    @Autowired
    UserRepository userRepository;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${public.url}")
    private String publicKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    public String generateToken(UserDetails userDetails) {
//        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
//
//        // ใช้ LinkedHashMap เพื่อรักษาลำดับของ claims
//        Map<String, Object> claims = new LinkedHashMap<>();
//        claims.put("name", user.get().getName());
//        claims.put("oid", user.get().getOid());
//        claims.put("email", user.get().getEmail());
//        claims.put("role", user.get().getRole());
//
//        // สร้าง JWT ด้วยการตั้งค่า issuer, issuedAt, และ expiration หลังจาก setClaims()
//        return Jwts.builder()
//                .setClaims(claims) // ตั้งค่า claims ทั้งหมดก่อน
//                .setIssuer("https://intproj23.sit.kmutt.ac.th/ssi1/") // ตั้งค่า issuer
//                .setIssuedAt(new Date(System.currentTimeMillis())) // ตั้งค่า issuedAt
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // ตั้งค่า expiration
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // ลงนามด้วยคีย์
//                .compact();
//    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", publicKey);
        claims.put("iat", new Date(System.currentTimeMillis()));
        claims.put("name", user.getName());
        claims.put("oid", user.getOid());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setHeaderParam("typ", "JWT").setClaims(claims).setSubject(subject)
                .setIssuedAt((Date) claims.get("iat"))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
