package br.com.jrsr.authenticationapi.helpers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtHelper {

    public static String generateToken(String email, Long expiration, String secretkey) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        return Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(exp)
                .signWith(Keys.hmacShaKeyFor(secretkey.getBytes()), SignatureAlgorithm.HS256).compact();
    }
}
