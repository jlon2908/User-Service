package com.arka.user_service.infraestructure.driven.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.arka.user_service.domain.model.*;

@Component
public class JwtUtil {
    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration}")
    private long jwtExpirationMs = 86400000; // Configuración de expiración a 24 horas

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user, Object domainUser) {
        Map<String, Object> claims = buildClaims(user, domainUser);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Object> buildClaims(User user, Object domainUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getEmail());
        claims.put("userId", user.getId().toString());
        claims.put("role", user.getRole().getName());
        claims.put("authorities", List.of(user.getRole().getName())); // 🔥 Esta línea habilita Spring Security

        // fullName siempre
        String fullName = extractFullName(domainUser);
        if (fullName != null) claims.put("fullName", fullName);

        String role = user.getRole().getName();
        switch (role) {
            case "CLIENT":
                if (domainUser instanceof Client) {
                    Client c = (Client) domainUser;
                    if (c.getLoyaltyPoints() != null) claims.put("loyaltyPoints", c.getLoyaltyPoints());
                }
                break;
            case "DRIVER":
                if (domainUser instanceof Driver) {
                    Driver d = (Driver) domainUser;
                    if (d.getAvailable() != null) claims.put("available", d.getAvailable());
                    if (d.getVehiclePlate() != null) claims.put("vehiclePlate", d.getVehiclePlate());
                }
                break;
            case "INVENTORY_MANAGER":
                if (domainUser instanceof InventoryManager) {
                    InventoryManager i = (InventoryManager) domainUser;
                    if (i.getAssignedWarehouseCode() != null) claims.put("assignedWarehouseCode", i.getAssignedWarehouseCode());
                    if (i.getPendingAudits() != null) claims.put("pendingAudits", i.getPendingAudits());
                }
                break;
            case "SALES_MANAGER":
                if (domainUser instanceof SalesManager) {
                    SalesManager s = (SalesManager) domainUser;
                    if (s.getRegion() != null) claims.put("region", s.getRegion());
                    if (s.getGoalAchieved() != null) claims.put("goalAchieved", s.getGoalAchieved());
                }
                break;
            case "ADMIN":
                // Solo fullName
                break;
        }
        return claims;
    }

    private String extractFullName(Object domainUser) {
        if (domainUser == null) return null;
        try {
            return (String) domainUser.getClass().getMethod("getFullName").invoke(domainUser);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .setAllowedClockSkewSeconds(60) // Permitir un margen de 60 segundos
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .setAllowedClockSkewSeconds(60)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
}
