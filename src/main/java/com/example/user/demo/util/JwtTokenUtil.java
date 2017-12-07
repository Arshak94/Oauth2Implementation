package com.example.user.demo.util;

import com.example.user.demo.model.Authority;
import com.example.user.demo.model.JwtUser;
import com.example.user.demo.model.User;
import com.example.user.demo.service.JwtUserFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable{
    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "aud";
    static final String CLAIM_KEY_CREATED = "iat";

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    @Autowired
    private TimeProvider timeProvider;

    @Value("${secret}")
    String secret;

    @Value("${expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) { return getClaimFromToken(token, Claims::getSubject); }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) { return getClaimFromToken(token, Claims::getExpiration); }

    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }

    public JwtUser getAllUserDataFromToken(String token){
        final Claims claims = getAllClaimsFromToken(token);

        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(new Long((Integer)claims.get("user_id")));
        jwtUser.setFirstname((String)claims.get("firsName"));
        jwtUser.setLastname((String)claims.get("lastName"));
        jwtUser.setEmail((String) claims.get("email"));
        jwtUser.setPassword(((String) claims.get("password")));
        jwtUser.setUsername((String) claims.get("username"));
        ArrayList<Object> objects = (ArrayList<Object>) claims.get("authorities");
        LinkedHashMap<String, String> linkedHashMap = null;

        List<Authority> authorities = new ArrayList<>();

        for (int i = 0; i < objects.size(); i++) {
            linkedHashMap= (LinkedHashMap<String, String>) objects.get(i);
            List<String> keys = new ArrayList<>(linkedHashMap.keySet());
            for (String key :
                    keys) {
                Authority authority = new Authority();
                authority.setName(linkedHashMap.get(key));
                authorities.add(authority);
            }
        }
        jwtUser.setAuthorities(authorities);

        return  jwtUser;
    }

    public Long getIdFromToken(String token){
        final Claims claims = getAllClaimsFromToken(token);
        return new Long((Integer)claims.get("user_id"));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return body;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(timeProvider.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(JwtUser jwtUser, Device device) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", jwtUser.getId());
        claims.put("firsName", jwtUser.getFirstname());
        claims.put("lastName", jwtUser.getLastname());
        claims.put("email", jwtUser.getEmail());
        claims.put("password", jwtUser.getPassword());
        claims.put("username", jwtUser.getUsername());
        claims.put("authorities", jwtUser.getAuthorities());
        claims.put("createdAt", jwtUser.getLastPasswordResetDate());
        claims.put("enabled", jwtUser.getEnabled());
        return doGenerateToken(claims, jwtUser.getUsername(), generateAudience(device));
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, String audience) {
        final Date createdDate = timeProvider.now();
        final Date expirationDate = calculateExpirationDateForAccesToken(createdDate);

        System.out.println("doGenerateToken " + createdDate);
        System.out.println(expirationDate);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secret)
                .setClaims(claims)
                .setSubject(subject)
                .setAudience(audience)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .compact();
    }
    public String accessTokenfromRefreshToken(String token, Device device) {
        final Date createdDate = timeProvider.now();
        final Date expirationDate = calculateExpirationDateForAccesToken(createdDate);
        JwtUser jwtUser = getAllUserDataFromToken(token);
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", jwtUser.getId());
        claims.put("firsName", jwtUser.getFirstname());
        claims.put("lastName", jwtUser.getLastname());
        claims.put("email", jwtUser.getEmail());
        claims.put("password", jwtUser.getPassword());
        claims.put("authorities", jwtUser.getAuthorities());
        claims.put("createdAt", jwtUser.getLastPasswordResetDate());
        claims.put("enabled", jwtUser.getEnabled());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setSubject(jwtUser.getUsername())
                .setAudience(generateAudience(device))
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = timeProvider.now();
        final Date expirationDate = calculateExpirationDateForRefreshToken(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }



    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        //final Date created = getIssuedAtDateFromToken(token);
        final Date expiration = getExpirationDateFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(expiration, user.getLastPasswordResetDate())
        );
    }

    private Date calculateExpirationDateForAccesToken(Date createdDate) {
        return new Date(createdDate.getTime() + expiration*120);
    }

    private Date calculateExpirationDateForRefreshToken(Date createdDate){
        return new Date(createdDate.getTime() + expiration*259200);
    }
}
