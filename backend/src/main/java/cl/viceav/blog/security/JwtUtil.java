package cl.viceav.blog.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
  private String secret;
  private int validityInMiliseconds = 3600000;
  private SecretKeySpec secretKeySpec;

  public JwtUtil(@Value("${jwt.secret}") String secret) {
    this.secret = secret;
    this.secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().getAlgorithm());
  }

  public String createToken(UserDetails userDetails) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMiliseconds);

    return Jwts.builder().claims().subject(userDetails.getUsername()).issuedAt(now).expiration(validity).and()
        .signWith(secretKeySpec)
        .compact();
  }

  public Claims getClaims(String token) {
    return Jwts.parser().verifyWith(secretKeySpec).build()
        .parseSignedClaims(token).getPayload();
  }

  public String getUsername(String token) {
    return getClaims(token).getSubject();
  }

  public boolean isTokenExpired(String token) {
    return getClaims(token).getExpiration().before(new Date());
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    String username = getUsername(token);
    return !isTokenExpired(token) && username.equals(userDetails.getUsername());
  }
}
