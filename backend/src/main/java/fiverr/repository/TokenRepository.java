package fiverr.repository;

import fiverr.entity.Token;
import fiverr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends RevisionRepository<Token, Long, Integer>, JpaRepository<Token, Long> {

    Optional<Token> findByUserAndToken(User user, String token);
}
