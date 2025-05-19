package pe.seek.app.customer.infrastructure.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pe.seek.app.customer.application.port.output.LoadSecurityPort;
import pe.seek.app.shared.constants.GenericErrors;
import pe.seek.app.shared.exception.GeneralException;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
class JwtProvider implements LoadSecurityPort {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    @Override
    public String generateToken(UserDetails userDetails) {
        log.info("Generating token for user: {}", userDetails.getUsername());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(Map.of(
                        "authorities", userDetails.getAuthorities(),
                        "phone", userDetails.getUsername()
                ))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(secret))
                .compact();
    }

    public Claims validateToken(String token) throws GeneralException {
        try {
            log.info("Validating token: {}", token);
            if (isTokenExpired(token)) throw new GeneralException(GenericErrors.TOKEN_IS_NOT_VALID);
            return getClaims(token);
        } catch (ExpiredJwtException e) {
            log.error("Token expired: {}", token);
            throw new GeneralException(GenericErrors.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("Token unsupported: {}", token);
            throw new GeneralException(GenericErrors.TOKEN_UNSUPPORTED);
        } catch (MalformedJwtException | SecurityException | IllegalArgumentException e) {
            log.error("Token is not valid: {}", token);
            throw new GeneralException(GenericErrors.TOKEN_IS_NOT_VALID);
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    private Key getKey(String s) {
        byte[] keyBytes = Decoders.BASE64.decode(s);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}