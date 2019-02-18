package fiverr.security;

import fiverr.util.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Authorization, Content-Type, Headers, X-Requested-With, Accept, Access-Control-Request-Methods, Access-Control-Request-Headers");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");

        String token = request.getHeader(JwtUtil.HEADER_STRING);

        if (token != null && token.startsWith(JwtUtil.TOKEN_PREFIX)) {
            SecurityContextHolder.getContext().setAuthentication(jwtUtil.parse(token));
        }

        filterChain.doFilter(request, response);
    }
}
