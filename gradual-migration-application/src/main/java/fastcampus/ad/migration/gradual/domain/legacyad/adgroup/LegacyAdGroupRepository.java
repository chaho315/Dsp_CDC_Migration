package fastcampus.ad.migration.gradual.domain.legacyad.adgroup;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegacyAdGroupRepository extends CrudRepository<LegacyAdGroup, Long> {
    List<LegacyAdGroup> findAllByCampaignIdAndDeletedAtIsNUll(Long campaignId);
}
