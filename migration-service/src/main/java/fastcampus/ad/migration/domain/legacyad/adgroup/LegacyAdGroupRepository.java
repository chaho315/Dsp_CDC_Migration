package fastcampus.ad.migration.domain.legacyad.adgroup;

import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegacyAdGroupRepository extends CrudRepository<LegacyAdGroup, Long> {

    List<LegacyAdGroup> findAllByCampaignIdAndDeletedAtIsNull(Long campaignId);
}
