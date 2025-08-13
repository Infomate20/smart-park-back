package smartPark.smart_park.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import smartPark.smart_park.models.entity.Utilisateur;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Autowired
    @Value("${jwt.secret}")
    private String secretKey;
    @Autowired
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Autowired
    @Value("${jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Utilisateur utilisateur) {
        return generateToken(new HashMap<>(), utilisateur);
    }

    public String generateToken(Map<String, Object> extraClaims, Utilisateur utilisateur) {
        return buildToken(extraClaims, utilisateur, jwtExpiration);
    }

    public String generateRefreshToken(Utilisateur utilisateur) {
        return buildToken(new HashMap<>(), utilisateur, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            Utilisateur utilisateur,
            long expiration
    ) {
        extraClaims.put("role", utilisateur.getRole().name());
        extraClaims.put("userId", utilisateur.getId());
        extraClaims.put("nom", utilisateur.getNomUtilisateur());
        extraClaims.put("prenom", utilisateur.getPrenom());

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(utilisateur.getNomUtilisateur())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
}