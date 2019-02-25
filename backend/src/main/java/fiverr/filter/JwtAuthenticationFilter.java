package fiverr.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fiverr.entity.User;
import fiverr.exception.ClientException;
import fiverr.exception.ServiceException;
import fiverr.util.JwtUtil;
import fiverr.util.Translator;
import fiverr.vos.AccountCredentials;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AccountCredentials user;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
            ));
        } catch (BadCredentialsException e) {
            response.setContentType("application/json;charset=UTF-8");
            ClientException exception = new ClientException(Translator.translate("exception.auth.bad-credentials"));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeQuietly(response, exception);
        } catch (InternalAuthenticationServiceException e) {
            if (e.getMessage().equals(Translator.translate("exception.auth.account-not-active"))) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                writeQuietly(response, new ClientException(e.getMessage()));
            }
            System.out.println("");
        } catch (IOException e) {
            response.setContentType("application/json;charset=UTF-8");
            ServiceException exception = new ServiceException(Translator.translate("exception.internal-error"));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writeQuietly(response, exception);
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        User user = (User) authResult.getPrincipal();

        String token = jwtUtil.getToken(user);

        response.addHeader(JwtUtil.HEADER_STRING, JwtUtil.TOKEN_PREFIX + " " + token);
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private void writeQuietly(HttpServletResponse response, Object object) {
        try {
            response.getWriter().write(convertObjectToJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
