package rocks.zipcode.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.ChannelMember;

/**
 * Spring Data JPA repository for the ChannelMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {}
