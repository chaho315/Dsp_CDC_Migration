package fastcampus.ad.migration.gradual.domain.recentad.user;

import fastcampus.ad.migration.gradual.domain.legacyad.user.LegacyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentUserRepository extends CrudRepository<RecentUser, Long> {
}
