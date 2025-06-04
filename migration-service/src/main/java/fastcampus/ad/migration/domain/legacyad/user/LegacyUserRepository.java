package fastcampus.ad.migration.domain.legacyad.user;

import fastcampus.ad.migration.domain.legacyad.user.LegacyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyUserRepository extends CrudRepository<LegacyUser, Long> {
}
