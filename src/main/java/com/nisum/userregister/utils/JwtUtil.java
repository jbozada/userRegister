package com.nisum.userregister.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import com.nisum.userregister.entities.UserNisum;
import com.nisum.userregister.services.UserRegisterService;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import io.jsonwebtoken.io.Decoders;

public class JwtUtil {
    
    private final Logger log = LoggerFactory.getLogger(UserRegisterService.class);
    
    private String key ="secretHashTokenMustHaveASize>=256";

    public String generateToken(UserNisum user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        try {
            String jwtToken = Jwts
                    .builder()
                    .setSubject(user.getName())
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration((new Date(System.currentTimeMillis() + 1000 * 60 * 1)))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
            return jwtToken;
        } catch (Exception ex) {
            log.error("Error al crear el token JWT: " + ex.getMessage());
            throw new GenericException("Error al crear el token JWT: " + ex.getMessage(),
                                       HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }
    
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        try {
            Date date =  extractExpiration(token);
            return date.before(new Date());
        } catch (ExpiredJwtException e) {
            log.error("Token expirado: " +e.getMessage());
            throw new GenericException("Token expirado",
                    HttpStatus.FORBIDDEN);
        }
    }

    public void decodeToken(String token){

        String[] chunks = token.split("\\.");
        SignatureAlgorithm sa = SignatureAlgorithm.HS256;
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), sa.getJcaName());
        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec, Decoders.BASE64URL );
        if (!validator.isValid(tokenWithoutSignature, signature) || isTokenExpired(token)) {
            log.error("Token inválido");
            throw new GenericException("Token inválido",
                    HttpStatus.FORBIDDEN);
        }

    }
}
