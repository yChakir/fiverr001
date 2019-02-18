package fiverr.service.impl;


import fiverr.entity.Token;
import fiverr.entity.User;
import fiverr.pojo.TokenType;
import fiverr.repository.TokenRepository;
import fiverr.service.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

    private final TokenRepository repository;

    public TokenServiceImpl(TokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public Token saveForUser(User user, TokenType type) {
        return repository.save(new Token(user, type));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Token> findByUserAndToken(User user, String token) {
        return repository.findByUserAndToken(user, token);
    }

    @Override
    public Token save(Token token) {
        return repository.save(token);
    }
}
