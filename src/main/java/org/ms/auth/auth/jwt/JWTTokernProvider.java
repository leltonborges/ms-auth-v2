package org.ms.auth.auth.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Service
public class JWTTokernProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token.expire-length}")
    private Long expireToken;

    @Autowired
    @Qualifier("userService")
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String userName, Set<String> roles){
        Claims claims = Jwts.claims()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis()+expireToken))
                .setIssuedAt(new Date(System.currentTimeMillis()));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token){
        return  Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken  = request.getHeader("Authorization");
        if(!bearerToken.equals(null) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replaceFirst("Bearer ", bearerToken);
        }
        return null;
    }

    public Boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            if(claimsJws.getBody().getExpiration().before(new Date(System.currentTimeMillis())))
                return false;

            return true;
        }catch (JwtException | IllegalArgumentException ex){
            return false;
        }
    }
}
