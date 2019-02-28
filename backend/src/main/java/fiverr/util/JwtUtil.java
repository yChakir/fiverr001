package fiverr.util;

import fiverr.entity.User;
import fiverr.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_SECRET = "fiverr001-secret";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    @Autowired
    private UserService userService;

    public String getToken(User user) {
        log.debug("getToken() :: user = {}", user);
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .claim("name", user.getName())
                .claim("surname", user.getSurname())
                .claim("avatar", user.getAvatar())
                .compact();

        log.debug("getToken() :: user = {}, token = {}", user, token);

        return token;
    }

    public Authentication parse(String token) {
        log.debug("parse() :: token = {}", token);
        Claims claims = Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token.substring(TOKEN_PREFIX.length()))
                .getBody();

        String username = claims.getSubject();
        log.debug("parse() :: user extracted = {}", username);

        User user = userService.findByEmail(username);
        log.debug("parse() :: user = {}", user);

        if (user == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());

    }
}
