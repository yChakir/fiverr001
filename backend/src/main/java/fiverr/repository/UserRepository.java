package fiverr.repository;

import fiverr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends RevisionRepository<User, Long, Integer>, JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
