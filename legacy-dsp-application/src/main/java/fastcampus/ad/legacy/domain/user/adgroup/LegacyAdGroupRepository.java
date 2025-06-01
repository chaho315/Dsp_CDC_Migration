package fastcampus.ad.legacy.domain.user.adgroup;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyAdGroupRepository extends CrudRepository<LegacyAdGroup, Long> {
}
