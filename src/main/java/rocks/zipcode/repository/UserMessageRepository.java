package rocks.zipcode.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.UserMessage;

/**
 * Spring Data JPA repository for the UserMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {}
