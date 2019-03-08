package fiverr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import fiverr.entity.Image;
import fiverr.entity.User;

@Repository
public interface ImageRepository extends RevisionRepository<Image, Long, Integer>, JpaRepository<Image, Long> {

    List<Image> findAllByUser(User user);

    Optional<Image> findByUserAndId(User user, Long id);

    Integer countAllByUser(User user);
}
