package fiverr.service;

import fiverr.entity.Token;
import fiverr.entity.User;
import fiverr.pojo.TokenType;

import java.util.Optional;

public interface TokenService {

    Token saveForUser(User user, TokenType type);

    Optional<Token> findByUserAndToken(User user, String token);

    Token save(Token token);
}
