package br.com.jrsr.authenticationapi.helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtHelper {

    public static String generateToken(String email, long expiration, String secretKey) {

        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(exp)
                .signWith((key), SignatureAlgorithm.HS256).compact();
    }

    public static Claims parseToken(String token, String secretKey) {

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
