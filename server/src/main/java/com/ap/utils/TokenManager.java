package com.ap.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;

public class TokenManager {
    private static final String SECRET = "ilia-hashem-parsa-secret";
    private static final Algorithm ALGO = Algorithm.HMAC256(SECRET);

    public static String generateToken(String username, boolean stayLoggedIn) {
        long now = System.currentTimeMillis();

        // 7 days if stayLoggedIn otherwise 1 hour
        long expire = now + (stayLoggedIn ? 1000L * 60 * 60 * 24 * 7 : 1000L * 60 * 60);

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(expire))
                .sign(ALGO);
    }

    public static String verifyAndGetUsername(String token) {
        try {
            return JWT.require(ALGO)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }
}