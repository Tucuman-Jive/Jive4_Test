package rocks.zipcode.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.ChannelMessage;

/**
 * Spring Data JPA repository for the ChannelMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChannelMessageRepository extends JpaRepository<ChannelMessage, Long> {}
