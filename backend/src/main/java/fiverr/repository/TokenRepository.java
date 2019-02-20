package fiverr.repository;

import fiverr.entity.Token;
import fiverr.entity.User;
import fiverr.pojo.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends RevisionRepository<Token, Long, Integer>, JpaRepository<Token, Long> {

    @Query("select t from Token t where t.user = ?1 and t.token = ?2 and t.type = ?3")
    Optional<Token> findByUserAndTokenAndType(User user, String token, TokenType type);

    @Query("select t from Token t where t.user = ?1 and t.type = ?2 and t.used = false")
    List<Token> findAllByUserAndTypeAndUsedIsFalse(User user, TokenType tokenType);
}
