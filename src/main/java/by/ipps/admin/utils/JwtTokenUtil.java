package by.ipps.admin.utils;

import by.ipps.admin.entity.User;
import by.ipps.admin.entity.UserAuth;
import by.ipps.admin.exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

  public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
  private static final long serialVersionUID = -2550185165626007488L;
  @Autowired
  private JwtRevokedTokensStore jwtRevokedTokensStore;
  //    @Value("${jwt.secret}")
  private String secret = "zxcasd";

  //retrieve username from jwt token
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getDateChangePassword(String token) {
    return getClaimFromToken(token);
  }

  //retrieve expiration date from jwt token
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public Date getClaimFromToken(String token) {
    final Claims claims = getAllClaimsFromToken(token);
    return new Date((Long) claims.get("DateLastChangePassword"));
  }

  //for retrieveing any information from token we will need the secret key
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  //check if the token has expired
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  //validate token
  public Boolean validateToken(String token, User userDetails) {
    final String username = getUsernameFromToken(token);
    final Date dateChangePasword = getDateChangePassword(token);
    return (username.equals(userDetails.getLogin()) && !isTokenExpired(token) && userDetails
        .getDateLastChangePassword().before(dateChangePasword) && userDetails.isEnabled() && !userDetails.isBlock());
  }

  public boolean validateToken(String token) throws InvalidJwtAuthenticationException {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

      if (claims.getBody().getExpiration().before(new Date())) {
        return false;
      }

      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
    }
  }

  public void revokeToken(String token) throws InvalidJwtAuthenticationException {
    jwtRevokedTokensStore.revokeToken(token);
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

  public String generateToken(UserAuth user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("UserName", user.getName());
    claims.put("PatronicName", user.getPatronicName());
    claims.put("SurName", user.getSurName());
    claims.put("Department", user.getDepartment());
    claims.put("Email", user.getEmail());
    claims.put("Position", user.getPosition());
    claims.put("Roles", user.getRoles());
    claims.put("DateLastChangePassword", new Date());
    return doGenerateToken(claims, user.getLogin());
  }
  // while creating the token -
  // 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
  // 2. Sign the JWT using the HS512 algorithm and secret key.
  // 3. According to JWS Compact
  // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
  //   compaction of the JWT to a URL-safe string
  private String doGenerateToken(Map<String, Object> claims, String userName) {
    return Jwts.builder()
        .setClaims(claims).setSubject(userName)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }
}
