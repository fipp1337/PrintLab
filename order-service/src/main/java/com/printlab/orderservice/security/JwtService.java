package com.printlab.orderservice.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.printlab.orderservice.entity.Role;
import com.printlab.orderservice.exception.AppErrorCode;
import com.printlab.orderservice.exception.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {

    private final byte[] secret;
    private final long expirationMs;
    private final long refreshExpirationMs;

    public JwtService(
            @Value("${store.jwt.secret}") String secret,
            @Value("${store.jwt.expiration-ms}") long expirationMs,
            @Value("${store.jwt.refresh-expiration-ms}") long refreshExpirationMs) {
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.expirationMs = expirationMs;
        this.refreshExpirationMs = refreshExpirationMs;
    }

    public String generateToken(SecurityFamily securityFamily) {
        return buildToken(String.valueOf(securityFamily.getId()), securityFamily.getEmail(), securityFamily.getRoles(), expirationMs, "access");
    }

    public String generateRefreshToken(SecurityFamily securityFamily) {
        return buildToken(String.valueOf(securityFamily.getId()), securityFamily.getEmail(), securityFamily.getRoles(), refreshExpirationMs, "refresh");
    }


    public String generateToken(Family family, List<Role> roles) {
        return buildToken(String.valueOf(family.getId()), family.getEmail(), roles, expirationMs, "access");
    }

    public String generateRefreshToken(Family family, List<Role> roles) {
        return buildToken(String.valueOf(family.getId()), family.getEmail(), roles, refreshExpirationMs, "refresh");
    }

    public String generateAdminToken(AdminUser admin) {
        return buildToken(String.valueOf(admin.getId()), admin.getEmail(), admin.getRoles().stream().toList(), expirationMs, "access");
    }

    public String generateAdminRefreshToken(AdminUser admin) {
        return buildToken(String.valueOf(admin.getId()), admin.getEmail(), admin.getRoles().stream().toList(), refreshExpirationMs, "refresh");
    }

    public SecurityFamily parseRefreshToken(String token) {
        try {
            SignedJWT signedJwt = SignedJWT.parse(token);
            if (!signedJwt.verify(new MACVerifier(secret))) {
                throw AppException.of(AppErrorCode.INVALID_TOKEN);
            }

            JWTClaimsSet claims = signedJwt.getJWTClaimsSet();

            String type = claims.getStringClaim("type");
            if (!"refresh".equals(type)) {
                throw AppException.of(AppErrorCode.INVALID_TOKEN);
            }

            Date expiration = claims.getExpirationTime();
            if (expiration == null || expiration.before(new Date())) {
                throw AppException.of(AppErrorCode.INVALID_TOKEN);
            }

            UUID id = UUID.fromString(claims.getSubject());
            String email = claims.getStringClaim("email");
            List<Role> roles = claims.getStringListClaim("roles").stream()
                    .map(Role::valueOf)
                    .toList();

            return new SecurityFamily(id, email, roles);
        } catch (ParseException | JOSEException e) {
            throw AppException.of(AppErrorCode.INVALID_TOKEN);
        }
    }
    public SecurityFamily parseToken(String token) {
        try {
            SignedJWT signedJwt = SignedJWT.parse(token);
            if (!signedJwt.verify(new MACVerifier(secret))) {
                throw AppException.of(AppErrorCode.INVALID_TOKEN);
            }

            JWTClaimsSet claims = signedJwt.getJWTClaimsSet();
            Date expiration = claims.getExpirationTime();
            if (expiration == null || expiration.before(new Date())) {
                throw AppException.of(AppErrorCode.INVALID_TOKEN);
            }

            UUID id = UUID.fromString(claims.getSubject());
            String email = claims.getStringClaim("email");
            List<Role> roles = claims.getStringListClaim("roles").stream()
                    .map(Role::valueOf)
                    .toList();

            return new SecurityFamily(id, email, roles);
        } catch (ParseException | JOSEException e) {
            throw AppException.of(AppErrorCode.INVALID_TOKEN);
        }

    }


    private String buildToken(String subject, String email, List<Role> roles, long expiration, String type) {
        try {
            Date now = new Date();
            List<String> roleNames = roles.stream().map(Role::name).toList();

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(subject)
                    .claim("email", email)
                    .claim("roles", roleNames)
                    .claim("type", type)
                    .issueTime(now)
                    .expirationTime(new Date(now.getTime() + expiration))
                    .build();

            SignedJWT signedJwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
            signedJwt.sign(new MACSigner(secret));
            return signedJwt.serialize();
        } catch (JOSEException e) {
            throw new IllegalStateException("Failed to generate JWT", e);
        }
    }
}
