package fiverr.service.impl;


import fiverr.entity.Token;
import fiverr.entity.User;
import fiverr.pojo.TokenType;
import fiverr.repository.TokenRepository;
import fiverr.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
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
    public Optional<Token> findByUserAndTokenAndType(User user, String token, TokenType type) {
        log.debug("findByUserAndTokenAndType() :: user = {}, token = {}, type = {}", user, token, type);
        return repository.findByUserAndTokenAndType(user, token, type);
    }

    @Override
    public Token save(Token token) {
        log.debug("save() :: token = {}", token);
        return repository.save(token);
    }

    @Override
    public List<Token> findAllByUserAndTypeAndNotUsed(User user, TokenType tokenType) {
        log.debug("findAllByUserAndTypeAndNotUsed() :: user = {}, type = {}", user, tokenType);
        return repository.findAllByUserAndTypeAndUsedIsFalse(user, tokenType);
    }
}
