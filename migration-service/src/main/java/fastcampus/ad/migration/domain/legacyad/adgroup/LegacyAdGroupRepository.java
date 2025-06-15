package fastcampus.ad.migration.domain.legacyad.adgroup;

import fastcampus.ad.migration.domain.legacyad.LegacyPageableRepository;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegacyAdGroupRepository extends LegacyPageableRepository<LegacyAdGroup> {

    List<LegacyAdGroup> findAllByCampaignIdAndDeletedAtIsNull(Long campaignId);

    List<LegacyAdGroup> findAllByCampaignIdInAndDeletedAtIsNull(List<Long> campaignIds);
}
