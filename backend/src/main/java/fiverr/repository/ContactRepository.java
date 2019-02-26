package fiverr.repository;

import fiverr.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends RevisionRepository<Contact, Long, Integer>, JpaRepository<Contact, Long> {
}
