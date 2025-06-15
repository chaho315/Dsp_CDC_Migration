package fastcampus.ad.migration.domain.legacyad.keyword;

import fastcampus.ad.migration.domain.legacyad.LegacyPageableRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegacyKeywordRepository extends LegacyPageableRepository<LegacyKeyword> {
    List<LegacyKeyword> findAllByAdGroupIdInAndDeletedAtIsNull(List<Long> adGroupIds);
}
