package fiverr.service;

import fiverr.entity.Token;
import fiverr.entity.User;
import fiverr.pojo.TokenType;

import java.util.List;
import java.util.Optional;

public interface TokenService {

    Token saveForUser(User user, TokenType type);

    Optional<Token> findByUserAndTokenAndType(User user, String token, TokenType type);

    Token save(Token token);

    List<Token> findAllByUserAndTypeAndNotUsed(User user, TokenType tokenType);
}
