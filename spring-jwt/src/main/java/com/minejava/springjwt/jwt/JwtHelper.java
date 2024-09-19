package com.minejava.springjwt.jwt;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.minejava.springjwt.document.RefreshToken;
import com.minejava.springjwt.document.User;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtHelper {
    static final String issuer = "MyApp";

    @Value("#{5 * 60 * 1000}")
    private int accessTokenExpirationMs;
    @Value("#{30 * 24 * 60 * 60 * 1000}")
    private long refreshTokenExpirationMs;

    private final Algorithm accessTokenAlgorithm;
    private final Algorithm refreshTokenAlgorithm;
    private final JWTVerifier accessTokenVerifier;
    private final JWTVerifier refreshTokenVerifier;

    public JwtHelper(@Value("${accessTokenSecret}") String accessTokenSecret, @Value("${refreshTokenSecret}") String refreshTokenSecret, @Value("${refreshTokenExpirationDays}") int refreshTokenExpirationDays) {
        //accessTokenExpirationMs = (long) accessTokenExpirationMinutes * 60 * 1000;
        refreshTokenExpirationMs = (long) refreshTokenExpirationDays * 24 * 60 * 60 * 1000;
    //public JwtHelper(@Value("${accessTokenSecret}") String accessTokenSecret, @Value("${refreshTokenSecret}") String refreshTokenSecret) {
        accessTokenAlgorithm = Algorithm.HMAC512(accessTokenSecret);
        refreshTokenAlgorithm = Algorithm.HMAC512(refreshTokenSecret);
        accessTokenVerifier = JWT.require(accessTokenAlgorithm)
                .withIssuer(issuer)
                .build();

        refreshTokenVerifier = JWT.require(refreshTokenAlgorithm)
                .withIssuer(issuer)
                .build();
    }

    //generate access token
    public String generateAccessToken(User user) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() * accessTokenExpirationMs))
                .sign(accessTokenAlgorithm);
    }

    //generate access token
    public String generateRefreshToken(User user, RefreshToken refreshToken) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getId())
                .withClaim("tokenId", refreshToken.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() * accessTokenExpirationMs))
                .sign(refreshTokenAlgorithm);
    }

    private Optional<DecodedJWT> decodeAccessToken(String token) {
        try {
            return Optional.of(accessTokenVerifier.verify(token));
        } catch (JWTVerificationException  e) {
            log.error("invalid access token", e);
        }
        return Optional.empty();
    }

    private Optional<DecodedJWT> decodeRefreshToken(String token) {
        try {
            return Optional.of(refreshTokenVerifier.verify(token));
        } catch (JWTVerificationException e) {
            log.error("invalid refresh token", e);
        }
        return Optional.empty();
    }

    // Mehtod to check validity of tokens
    public boolean validateAccessToken(String token) {
        return decodeAccessToken(token).isPresent();
    }

        // Convenient methods to extract token values?

    public boolean validateRefreshToken(String token) {
        return decodeRefreshToken(token).isPresent();
    }

    public String getUserIdFromAccessToken(String token) {
        return decodeAccessToken(token).get().getSubject();
    }

    public String getUserIdFromRefreshToken(String token) {
        return decodeRefreshToken(token).get().getSubject();
    }

    public String getTokenIdFromRefreshToken(String token) {
        return decodeRefreshToken(token).get().getClaim("tokenId").asString();
    }
}
